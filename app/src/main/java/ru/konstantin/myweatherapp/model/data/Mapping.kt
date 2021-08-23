package ru.konstantin.myweatherapp.model.data

import ru.konstantin.myweatherapp.room.HistoryEntity

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<WeatherHistory> {
    return entityList.map {
        WeatherHistory("", it.temperature, 0.0, it.condition)
    }
}

fun convertWeatherToEntity(weather: WeatherHistory ): HistoryEntity {
    return HistoryEntity(0, weather.city, weather.temperature, weather.condition)
}
