package ru.konstantin.myweatherapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Callback
import retrofit2.Retrofit
import ru.konstantin.myweatherapp.model.data.GeoCity
import ru.konstantin.myweatherapp.model.data.WeatherBigData

class Network {

    private val contentType: MediaType = MediaType.get("application/json")
    @ExperimentalSerializationApi
    val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl("http://api.weatherapi.com/")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build().create(WeatherApi::class.java)

    @ExperimentalSerializationApi
    fun getWeather(geoCity: GeoCity, callback: Callback<WeatherBigData>) {
        val apiAccessKey = "ced826e111584f05b9c154820212507"
        weatherApi.getWeather(apiAccessKey, "${geoCity.latitude},${geoCity.longitude}", "yes", "ru").enqueue(callback)
        println()
    }

}