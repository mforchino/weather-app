package com.weather.mforchino.domain.repository

import com.weather.mforchino.model.LocationUser
import kotlinx.coroutines.flow.Flow

interface TrackerRepository {
     fun getLatLongUser(): Flow<LocationUser>
}