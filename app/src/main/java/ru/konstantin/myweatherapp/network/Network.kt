package ru.konstantin.myweatherapp.network

import okhttp3.OkHttpClient
import okhttp3.Request
import ru.konstantin.myweatherapp.model.data.GeoCity
import java.net.URL

class Network {

    //TODO Will create own method for creating URL
    fun getWeather(geoCity: GeoCity): String {
        val client = OkHttpClient()
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
}