package ru.konstantin.myweatherapp.model

import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData

sealed class AppState {
    data class Success(val weatherData: List<GeoCity>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
