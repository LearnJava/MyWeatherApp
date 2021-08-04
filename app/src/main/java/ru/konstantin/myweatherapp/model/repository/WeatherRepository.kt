package ru.konstantin.myweatherapp.model.repository

import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData

interface WeatherRepository {
    fun getWeatherFromServer(geoCity: GeoCity): String
    fun getWeatherFromLocalStorage(): WeatherBigData
}