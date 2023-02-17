package com.weather.mforchino.domain.api

import com.weather.mforchino.model.CityWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {

    @GET("weather/")
    suspend fun getCityWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String ="fed6d6c2792186b5e1c1b7749f857890"
    ): Response<CityWeather>

}