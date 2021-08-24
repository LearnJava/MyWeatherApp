package ru.konstantin.myweatherapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WeatherBigData(
    @SerialName("current")
    val current: @RawValue Current? = Current(),
    @SerialName("location")
    val location: @RawValue Location? = Location()
): Parcelable {
    @Parcelize
    @Serializable
    data class Current(
        @SerialName("air_quality")
        val airQuality : @RawValue AirQuality? = AirQuality(),
        @SerialName("cloud")
        val cloud : @RawValue Int? = 0,
        @SerialName("condition")
        val condition : @RawValue Condition? = Condition(),
        @SerialName("feelslike_c")
        val feelslikeC : @RawValue Double? = 0.0,
        @SerialName("feelslike_f")
        val feelslikeF : @RawValue Double? = 0.0,
        @SerialName("gust_kph")
        val gustKph : @RawValue Double? = 0.0,
        @SerialName("gust_mph")
        val gustMph : @RawValue Double? = 0.0,
        @SerialName("humidity")
        val humidity : @RawValue Int? = 0,
        @SerialName("is_day")
        val isDay : @RawValue Int? = 0,
        @SerialName("last_updated")
        val lastUpdated : @RawValue String? = "",
        @SerialName("last_updated_epoch")
        val lastUpdatedEpoch : @RawValue Int? = 0,
        @SerialName("precip_in")
        val precipIn : @RawValue Double? = 0.0,
        @SerialName("precip_mm")
        val precipMm : @RawValue Double? = 0.0,
        @SerialName("pressure_in")
        val pressureIn : @RawValue Double? = 0.0,
        @SerialName("pressure_mb")
        val pressureMb : @RawValue Double? = 0.0,
        @SerialName("temp_c")
        val tempC : @RawValue Double? = 23.9,
        @SerialName("temp_f")
        val tempF : @RawValue Double? = 0.0,
        @SerialName("uv")
        val uv : @RawValue Double? = 0.0,
        @SerialName("vis_km")
        val visKm : @RawValue Double? = 0.0,
        @SerialName("vis_miles")
        val visMiles : @RawValue Double? = 0.0,
        @SerialName("wind_degree")
        val windDegree : @RawValue Int? = 0,
        @SerialName("wind_dir")
        val windDir : @RawValue String? = "",
        @SerialName("wind_kph")
        val windKph : @RawValue Double? = 0.0,
        @SerialName("wind_mph")
        val windMph : @RawValue Double? = 0.0
    ): Parcelable {
        @Parcelize
        @Serializable
        data class AirQuality(
            @SerialName("co")
            val co : @RawValue Double? = 0.0,
            @SerialName("gb-defra-index")
            val gbDefraIndex : @RawValue Int? = 0,
            @SerialName("no2")
            val no2 : @RawValue Double? = 0.0,
            @SerialName("o3")
            val o3 : @RawValue Double? = 0.0,
            @SerialName("pm10")
            val pm10 : @RawValue Double? = 0.0,
            @SerialName("pm2_5")
            val pm25 : @RawValue Double? = 0.0,
            @SerialName("so2")
            val so2 : @RawValue Double? = 0.0,
            @SerialName("us-epa-index")
            val usEpaIndex : @RawValue Int? = 0
        ): Parcelable

        @Parcelize
        @Serializable
        data class Condition(
            @SerialName("code")
            val code : @RawValue Int? = 0,
            @SerialName("icon")
            val icon : @RawValue String? = "",
            @SerialName("text")
            val text : @RawValue String? = "Переменная облачность"
        ): Parcelable
    }

    @Parcelize
    @Serializable
    data class Location(
        @SerialName("country")
        val country : @RawValue String? = "Russia",
        @SerialName("lat")
        val lat : @RawValue Double? = 59.89,
        @SerialName("localtime")
        val localtime : @RawValue String? = "",
        @SerialName("localtime_epoch")
        val localtimeEpoch : @RawValue Int? = 0,
        @SerialName("lon")
        val lon : @RawValue Double? = 30.26,
        @SerialName("name")
        val name : @RawValue String? = "Saint Petersburg",
        @SerialName("region")
        val region : @RawValue String? = "",
        @SerialName("tz_id")
        val tzId : @RawValue String? = ""
    ): Parcelable
}