package ru.konstantin.myweatherapp.model.repository

import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.network.Network

class WeatherRepositoryImpl : WeatherRepository {
    override fun getWeatherFromServer(geoCity: GeoCity): String {
        return Network().getWeather(geoCity)
    }

    fun getWeatherFromServerByLatAndLon(lat: String, lon: String): String {

        return Network().getWeatherByLatAndLon(lat, lon)
    }
}