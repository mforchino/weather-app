package com.weather.mforchino

import androidx.compose.runtime.Stable
import com.weather.mforchino.model.CityWeather
import com.weather.mforchino.model.LocationUser


@Stable
data class MainScreenData(
    val cities: List<City> = listOf(
        City("Mi Ubicación", "0", "0"),
        City("Montevideo", "-34.828336810409155", "-56.18707757013124"),
        City("Londres", "51.51373673294905", "-0.12646481636766754"),
        City("San Pablo", "37.9640166941696", "-122.34646484649679"),
        City("Buenos Aires", "-34.6084571677726", "-58.45080998022256"),
        City("Munich", "48.13513393278908", "11.5820935307677"),
        ),
    val citySelected: City? = null,
    val cityWeather: CityWeather? = null,
    val userCurrentLocation: LocationUser? = null,
)

class City(
    val name: String,
    val lat: String,
    val long: String
)