package ru.konstantin.myweatherapp.service

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.model.repository.WeatherRepositoryImpl

class WeatherService {
    private val weatherRepository = WeatherRepositoryImpl()
    fun getCityWeather(geoCity: GeoCity): WeatherBigData {
        val weather = weatherRepository.getWeatherFromServer(geoCity)
        return Json.decodeFromString<WeatherBigData>(weather)
    }
}