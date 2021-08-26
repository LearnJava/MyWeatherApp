package ru.konstantin.myweatherapp.model.repository

import ru.konstantin.myweatherapp.model.data.WeatherHistory
import ru.konstantin.myweatherapp.model.data.convertHistoryEntityToWeather
import ru.konstantin.myweatherapp.model.data.convertWeatherToEntity
import ru.konstantin.myweatherapp.room.HistoryDao

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {
    override fun getAllHistory(): List<WeatherHistory> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: WeatherHistory ) {
        return localDataSource.insert(convertWeatherToEntity(weather))
    }
}