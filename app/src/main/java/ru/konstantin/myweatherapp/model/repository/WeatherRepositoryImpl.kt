package ru.konstantin.myweatherapp.model.repository

import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Callback
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.network.Network

class WeatherRepositoryImpl : WeatherRepository {
    @ExperimentalSerializationApi
    override fun getWeatherFromServer(geoCity: GeoCity, callback: Callback<WeatherBigData>) {
        Network().getWeather(geoCity, callback)
    }
}