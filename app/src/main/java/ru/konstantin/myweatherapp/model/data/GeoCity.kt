package ru.konstantin.myweatherapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoCity(val cityName: String = "", val latitude: Double, val longitude: Double): Parcelable