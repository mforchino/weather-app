package com.weather.mforchino.domain.repository.impl

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.weather.mforchino.domain.repository.TrackerRepository
import com.weather.mforchino.model.LocationUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

internal class TrackerRepositoryImpl(
    private val context: Context,
) : TrackerRepository {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override fun getLatLongUser(): Flow<LocationUser> = callbackFlow {
        val locationCallback = LocationCallback(this)
        val samplingInterval = 1000L

        val locationRequest: LocationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, samplingInterval)
                .setWaitForAccurateLocation(false)
                .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("lATLONG: ", "Consider calling requestPermissions")
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        ).addOnCompleteListener { addTask ->
            Log.i("LATLONG: ", "Location callback request with success ${addTask.isSuccessful}")

        }.addOnFailureListener { e ->
            Log.e("lATLONG: ", "Location callback request exception $e")
            close(e)
        }

        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener {
                    Log.i("lATLONG: ", "Location callback removed with success")
                }
        }
    }
}

open class LocationCallback(private val producerScope: ProducerScope<LocationUser>) :
    com.google.android.gms.location.LocationCallback() {
    override fun onLocationResult(location: LocationResult) {
        super.onLocationResult(location)
        location.lastLocation?.let {
            producerScope.trySend(
                LocationUser(
                    lat = it.latitude.toString(),
                    long = it.longitude.toString()
                )
            )
        }
    }
}