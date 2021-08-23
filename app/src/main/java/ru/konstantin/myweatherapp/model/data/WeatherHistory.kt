package ru.konstantin.myweatherapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WeatherHistory(

    var city: String = "",
    val temperature: Double = 0.0,
    val feelsLike: Double = 0.0,
    val condition: String = "sunny"
    ) : Parcelable