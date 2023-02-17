package com.weather.mforchino.view.ui.common

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


private val LocationPermissions =
    listOf(Manifest.permission.ACCESS_COARSE_LOCATION)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionTracker(
    shouldRequestPermissions: Boolean = true,
    onPermissionStateChanged: (Boolean) -> Unit
) {

    var locationRequested by remember { mutableStateOf(false) }
    val locationState =
        rememberMultiplePermissionsState(LocationPermissions) { locationRequested = true }

    var shouldShowLocationPermissionsDialog by remember { mutableStateOf(false) }

    LaunchedEffect(
        locationRequested,
        shouldRequestPermissions
    ) {
        when {
            shouldRequestPermissions.not() -> onPermissionStateChanged(
                locationState.allPermissionsGranted
            )
            locationState.allPermissionsGranted.not() && locationRequested -> onPermissionStateChanged(
                false
            )
            locationState.allPermissionsGranted.not() ->
                shouldShowLocationPermissionsDialog = true
            else -> onPermissionStateChanged(true)
        }
    }
    if (shouldShowLocationPermissionsDialog) ConfirmationDialog(
        title = "¿ACTIVAS LA LOCALIZACIÓN?",
        message = "Necesitamos saber tu ubicación para mostrarle el clima",
        cancellable = false,
        positiveText = "OK",
        onPositiveClicked = {
            locationState.launchMultiplePermissionRequest()
        },
        dismissCallback = { shouldShowLocationPermissionsDialog = false }
    )
}

fun Context.showPermissionsDeniedPrettyToast(message: String) =
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
