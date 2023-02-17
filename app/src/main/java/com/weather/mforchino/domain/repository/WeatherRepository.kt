package com.weather.mforchino.domain.repository

import com.weather.mforchino.model.CityWeather

interface WeatherRepository {
    suspend fun getCityWeather(lat: String, long: String): CityWeather?
}