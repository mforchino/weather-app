package com.weather.mforchino

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.weather.mforchino.domain.repository.TrackerRepository
import androidx.lifecycle.LifecycleOwner
import com.weather.mforchino.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KMutableProperty0

@HiltViewModel
internal class MainScreenViewModel @Inject constructor(
    private val locationRepository: TrackerRepository,
    private val weatherRepository: WeatherRepository
) :
    ViewModel() {

    var state by mutableStateOf(UiState<MainScreenData>(loading = false))
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                data = MainScreenData(),
                loading = false
            )
        }
    }

    fun setCitiSelected(city: City, lifecycleOwner: LifecycleOwner, myLocation: Boolean) =
        viewModelScope.launch {
            ::state.transform { it.copy(citySelected = city) }
            if (myLocation) {
                getMyLocationWeather(lifecycleOwner)

            } else {
                getCityWeather(city.lat, city.long)
            }
        }

    private suspend fun getMyLocationWeather(lifecycleOwner: LifecycleOwner) =
        viewModelScope.launch {
            state.loading = true
            locationRepository.getLatLongUser()
                .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach { location ->
                    ::state.transform {
                        it.copy(userCurrentLocation = location)
                    }
                    getCityWeather(lat = location.lat, long = location.long)
                }
                .launchIn(lifecycleOwner.lifecycleScope)
        }

    private suspend fun getCityWeather(lat: String, long: String) = viewModelScope.launch {
        state.loading = true

        weatherRepository.getCityWeather(lat, long).let { cityWeather ->
            Log.i("WEATHER INFO", "$cityWeather")
            state.loading = false
            ::state.transform { it.copy(cityWeather = cityWeather) }
        }
    }
}

@Stable
data class UiState<T>(var loading: Boolean, val error: Error? = null, val data: T? = null) {

    data class Error(val message: String? = null)
}

suspend inline fun <T> KMutableProperty0<UiState<T>>.transform(crossinline transformation: suspend (T) -> T) =
    get().data?.let { data -> set(get().copy(data = transformation(data))) }
