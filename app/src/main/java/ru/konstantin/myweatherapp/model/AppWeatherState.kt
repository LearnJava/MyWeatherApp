package ru.konstantin.myweatherapp.model

import ru.konstantin.myweatherapp.model.data.WeatherBigData

//TODO Пока не понимаю как это работает, создал по аналогии с другим классом. Хотя наверное можно через дженерики объеденить 2 этих класса
sealed class AppWeatherState {
    data class Success(val weatheBigDate: WeatherBigData): AppWeatherState()
    class Error(val error: Throwable) : AppWeatherState()
    object Loading : AppWeatherState()
}