package ru.konstantin.myweatherapp.model

import ru.konstantin.myweatherapp.model.data.WeatherHistory


sealed class AppState {
    data class Success(val weatherData: List<WeatherHistory>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
