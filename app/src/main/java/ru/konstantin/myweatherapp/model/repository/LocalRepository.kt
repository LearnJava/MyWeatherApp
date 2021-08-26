package ru.konstantin.myweatherapp.model.repository

import ru.konstantin.myweatherapp.model.data.WeatherHistory


interface LocalRepository {
    fun getAllHistory(): List<WeatherHistory >
    fun saveEntity(weather: WeatherHistory )
}