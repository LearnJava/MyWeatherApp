package ru.konstantin.myweatherapp.model.repository

import ru.konstantin.myweatherapp.model.data.WeatherBigData

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): WeatherBigData {
        return WeatherBigData()
    }

    override fun getWeatherFromLocalStorage(): WeatherBigData {
        return WeatherBigData()
    }
}