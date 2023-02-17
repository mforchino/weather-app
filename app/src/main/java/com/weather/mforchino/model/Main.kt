package com.weather.mforchino.model

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
) {
    val tempCelsius: Int
        get() = kelvinToCelsius(temp)

    val tempMaxCelsius: Int
        get() = kelvinToCelsius(temp_max)

    val tempMinCelsius: Int
        get() = kelvinToCelsius(temp_min)

    private fun kelvinToCelsius(value: Double): Int =
        (value - 273.15).toInt()

}