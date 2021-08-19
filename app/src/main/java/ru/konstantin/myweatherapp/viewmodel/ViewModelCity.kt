package ru.konstantin.myweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.konstantin.myweatherapp.model.AppState
import ru.konstantin.myweatherapp.view.capitals
import ru.konstantin.myweatherapp.view.russianCities

class ViewModelCity: ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getCityList(isRussianCities: Boolean) {
        liveDataToObserve.postValue(AppState.Loading)
        if (isRussianCities) {
            liveDataToObserve.postValue(AppState.Success(russianCities))
        } else {
            liveDataToObserve.postValue(AppState.Success(capitals))
        }
    }
}