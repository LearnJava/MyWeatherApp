package ru.konstantin.myweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.konstantin.myweatherapp.model.AppState
import ru.konstantin.myweatherapp.model.repository.WeatherRepository
import ru.konstantin.myweatherapp.model.repository.WeatherRepositoryImpl
import ru.konstantin.myweatherapp.view.russianCities
import java.lang.Thread.sleep

class MainViewModel(private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()) :
    ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    private var counter: Int = 0

    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

//    fun getWeatherFromLocalSource() {
//        liveDataToObserve.value = AppState.Loading
//        Thread {
//            sleep(1000)
//            counter++
//            liveDataToObserve.postValue(AppState.Success(weatherRepository.getWeatherFromLocalStorage()))
//        }.start()
//    }

    fun getWeatherFromRemoteSource() {
        liveDataToObserve.postValue(AppState.Loading)
//        weatherRepository.getWeatherFromServer()
        counter++
        liveDataToObserve.postValue(AppState.Success(russianCities))
    }
}