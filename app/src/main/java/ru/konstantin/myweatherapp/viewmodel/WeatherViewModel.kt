package ru.konstantin.myweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.konstantin.myweatherapp.model.AppWeatherState
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData
import ru.konstantin.myweatherapp.model.repository.WeatherRepository
import ru.konstantin.myweatherapp.model.repository.WeatherRepositoryImpl

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class WeatherViewModel(private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()) :
    ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppWeatherState> = MutableLiveData()

    fun getData(): LiveData<AppWeatherState> {
        return liveDataToObserve
    }

    fun getWeatherFromRemoteSource(geoCity: GeoCity) {
        liveDataToObserve.postValue(AppWeatherState.Loading)
        weatherRepository.getWeatherFromServer(geoCity, callback)
    }

    private val callback = object : Callback<WeatherBigData> {
        override fun onResponse(call: Call<WeatherBigData>, response: Response<WeatherBigData>) {
            val weatherBigData = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && weatherBigData != null) {
                    checkResponse(weatherBigData)
                } else {
                    AppWeatherState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<WeatherBigData>, t: Throwable) {
            liveDataToObserve.postValue(
                AppWeatherState.Error(
                    Throwable(
                        t.message ?: REQUEST_ERROR
                    )
                )
            )
        }
    }

    fun checkResponse(weatherBigData: WeatherBigData): AppWeatherState {
        return if (weatherBigData.current?.tempC == null || weatherBigData.current.feelslikeC == null || weatherBigData.current.condition?.text?.isBlank() == true) {
            AppWeatherState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppWeatherState.Success(weatherBigData)
        }
    }
}