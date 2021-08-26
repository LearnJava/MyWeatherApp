package ru.konstantin.myweatherapp.model.repository

import retrofit2.Callback
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData

interface WeatherRepository {
    fun getWeatherFromServer(geoCity: GeoCity, callback: Callback<WeatherBigData>)
}