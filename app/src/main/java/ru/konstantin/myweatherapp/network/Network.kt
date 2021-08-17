package ru.konstantin.myweatherapp.network

import okhttp3.OkHttpClient
import okhttp3.Request
import ru.konstantin.myweatherapp.model.data.GeoCity
import java.net.URL
import java.util.concurrent.TimeUnit

class Network {

    fun getWeather(geoCity: GeoCity): String {
        val client = OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS)
            .build()

        val apiAccessKey = "ced826e111584f05b9c154820212507"
        val url =
            URL("http://api.weatherapi.com/v1/current.json?key=${apiAccessKey}&q=${geoCity.latitude},${geoCity.longitude}&aqi=yes&lang=ru")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()

        return response.body?.string()?:""
    }

    fun getWeatherByLatAndLon(latitude: String, longitude: String): String {
        val client = OkHttpClient()
        val apiAccessKey = "ced826e111584f05b9c154820212507"
        val url =
            URL("http://api.weatherapi.com/v1/current.json?key=${apiAccessKey}&q=${latitude},${longitude}&aqi=yes&lang=ru")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()

        return response.body?.string()?:""
    }
}