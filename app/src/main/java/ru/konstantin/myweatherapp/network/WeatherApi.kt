package ru.konstantin.myweatherapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.konstantin.myweatherapp.model.data.WeatherBigData

interface WeatherApi {

    @GET("v1/current.json")
    fun getWeather(
        @Query("key") apiAccessKey: String,
        @Query("q") query: String,
        @Query("aqi") aqi: String,
        @Query("lang") lang: String
    ): Call<WeatherBigData>
}