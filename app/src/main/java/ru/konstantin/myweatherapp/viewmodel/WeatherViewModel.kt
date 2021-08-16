package ru.konstantin.myweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.konstantin.myweatherapp.model.AppState
import ru.konstantin.myweatherapp.model.AppWeatherState
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.service.WeatherService
import ru.konstantin.myweatherapp.view.russianCities

class WeatherViewModel(private val weatherService: WeatherService = WeatherService()) :
    ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppWeatherState> = MutableLiveData()

    private var counter: Int = 0

    fun getData(): LiveData<AppWeatherState> {
        return liveDataToObserve
    }

    fun getWeatherFromRemoteSource(geoCity: GeoCity) {
        liveDataToObserve.postValue(AppWeatherState.Loading)
        GlobalScope.launch {
            val weather = weatherService.getCityWeather(geoCity)
            counter++
            liveDataToObserve.postValue(AppWeatherState.Success(weather))
        }
    }

    fun getWeatherFromRemoteSourceByLatAndLon(lat: String, lon: String) {
        liveDataToObserve.postValue(AppWeatherState.Loading)
            val weather = weatherService.getCityWeatherByLatAndLon(lat, lon)
            liveDataToObserve.postValue(AppWeatherState.Success(weather))
    }
}