package ru.konstantin.myweatherapp.model

import ru.konstantin.myweatherapp.model.data.WeatherBigData

sealed class AppState {
    data class Success(val weatherData: WeatherBigData) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
