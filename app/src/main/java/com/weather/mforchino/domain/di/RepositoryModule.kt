package com.weather.mforchino.domain.di

import android.content.Context
import com.weather.mforchino.domain.api.WeatherAPI
import com.weather.mforchino.domain.repository.TrackerRepository
import com.weather.mforchino.domain.repository.WeatherRepository
import com.weather.mforchino.domain.repository.impl.TrackerRepositoryImpl
import com.weather.mforchino.domain.repository.impl.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context,
    ): TrackerRepository = TrackerRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherAPI: WeatherAPI,
    ): WeatherRepository = WeatherRepositoryImpl(weatherAPI)
}