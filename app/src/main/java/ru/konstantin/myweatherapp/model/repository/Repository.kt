package ru.konstantin.myweatherapp.model.repository

import ru.konstantin.myweatherapp.model.data.WeatherBigData

interface Repository {
    fun getWeatherFromServer(): WeatherBigData
    fun getWeatherFromLocalStorage(): WeatherBigData
}