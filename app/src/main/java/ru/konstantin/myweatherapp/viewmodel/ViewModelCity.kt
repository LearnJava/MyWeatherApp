package ru.konstantin.myweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.konstantin.myweatherapp.model.AppStateCity
import ru.konstantin.myweatherapp.view.capitals
import ru.konstantin.myweatherapp.view.russianCities

class ViewModelCity: ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppStateCity> = MutableLiveData()

    fun getData(): LiveData<AppStateCity> {
        return liveDataToObserve
    }

    fun getCityList(isRussianCities: Boolean) {
        liveDataToObserve.postValue(AppStateCity.Loading)
        if (isRussianCities) {
            liveDataToObserve.postValue(AppStateCity.Success(russianCities))
        } else {
            liveDataToObserve.postValue(AppStateCity.Success(capitals))
        }
    }
}