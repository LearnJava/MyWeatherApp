package ru.konstantin.myweatherapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.konstantin.myweatherapp.model.data.WeatherBigData

//http://api.weatherapi.com/v1/current.json?key=${apiAccessKey}&q=${geoCity.latitude},${geoCity.longitude}&aqi=yes&lang=ru
interface WeatherApi {

    @GET("v1/current.json")
    fun getWeather(
        @Query("key") apiAccessKey: String,
        @Query("q") query: String,
        @Query("aqi") aqi: String,
        @Query("lang") lang: String
    ): Call<WeatherBigData>
}