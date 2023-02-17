package com.weather.mforchino.domain.repository.impl

import com.weather.mforchino.domain.api.WeatherAPI
import com.weather.mforchino.domain.repository.WeatherRepository
import com.weather.mforchino.model.CityWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class WeatherRepositoryImpl(
    private val weatherAPI: WeatherAPI
) : WeatherRepository {

    override suspend fun getCityWeather(lat: String, long: String): CityWeather? =
        withContext(Dispatchers.IO) {
            weatherAPI.getCityWeather(lat, long).body()
        }

}
