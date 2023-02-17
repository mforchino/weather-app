package com.weather.mforchino.domain.di

import com.weather.mforchino.BuildConfig
import com.weather.mforchino.domain.api.WeatherAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


annotation class InterceptedRetrofitClient

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @InterceptedRetrofitClient
    @Provides
    @Singleton
    fun provideApiRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideWeatherApi(@InterceptedRetrofitClient retrofit: Retrofit): WeatherAPI =
        retrofit.create(WeatherAPI::class.java)

}