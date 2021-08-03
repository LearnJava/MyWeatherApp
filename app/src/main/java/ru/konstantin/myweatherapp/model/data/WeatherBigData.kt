package ru.konstantin.myweatherapp.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Link to get weather https://www.weatherapi.com/api-explorer.aspx
//http://api.weatherapi.com/v1/current.json?key=ced826e111584f05b9c154820212507&q=Saint-Petersburg&aqi=yes&lang=ru
@Serializable
data class WeatherBigData(
    @SerialName("current")
    val current: Current? = Current(),
    @SerialName("location")
    val location: Location? = Location()
) {
    @Serializable
    data class Current(
        @SerialName("air_quality")
        val airQuality: AirQuality? = AirQuality(),
        @SerialName("cloud")
        val cloud: Int? = 0,
        @SerialName("condition")
        val condition: Condition? = Condition(),
        @SerialName("feelslike_c")
        val feelslikeC: Double? = 0.0,
        @SerialName("feelslike_f")
        val feelslikeF: Double? = 0.0,
        @SerialName("gust_kph")
        val gustKph: Double? = 0.0,
        @SerialName("gust_mph")
        val gustMph: Double? = 0.0,
        @SerialName("humidity")
        val humidity: Int? = 0,
        @SerialName("is_day")
        val isDay: Int? = 0,
        @SerialName("last_updated")
        val lastUpdated: String? = "",
        @SerialName("last_updated_epoch")
        val lastUpdatedEpoch: Int? = 0,
        @SerialName("precip_in")
        val precipIn: Double? = 0.0,
        @SerialName("precip_mm")
        val precipMm: Double? = 0.0,
        @SerialName("pressure_in")
        val pressureIn: Double? = 0.0,
        @SerialName("pressure_mb")
        val pressureMb: Double? = 0.0,
        @SerialName("temp_c")
        val tempC: Double? = 23.9,
        @SerialName("temp_f")
        val tempF: Double? = 0.0,
        @SerialName("uv")
        val uv: Double? = 0.0,
        @SerialName("vis_km")
        val visKm: Double? = 0.0,
        @SerialName("vis_miles")
        val visMiles: Double? = 0.0,
        @SerialName("wind_degree")
        val windDegree: Int? = 0,
        @SerialName("wind_dir")
        val windDir: String? = "",
        @SerialName("wind_kph")
        val windKph: Double? = 0.0,
        @SerialName("wind_mph")
        val windMph: Double? = 0.0
    ) {
        @Serializable
        data class AirQuality(
            @SerialName("co")
            val co: Double? = 0.0,
            @SerialName("gb-defra-index")
            val gbDefraIndex: Int? = 0,
            @SerialName("no2")
            val no2: Double? = 0.0,
            @SerialName("o3")
            val o3: Double? = 0.0,
            @SerialName("pm10")
            val pm10: Double? = 0.0,
            @SerialName("pm2_5")
            val pm25: Double? = 0.0,
            @SerialName("so2")
            val so2: Double? = 0.0,
            @SerialName("us-epa-index")
            val usEpaIndex: Int? = 0
        )

        @Serializable
        data class Condition(
            @SerialName("code")
            val code: Int? = 0,
            @SerialName("icon")
            val icon: String? = "",
            @SerialName("text")
            val text: String? = "Переменная облачность"
        )
    }

    @Serializable
    data class Location(
        @SerialName("country")
        val country: String? = "Russia",
        @SerialName("lat")
        val lat: Double? = 59.89,
        @SerialName("localtime")
        val localtime: String? = "",
        @SerialName("localtime_epoch")
        val localtimeEpoch: Int? = 0,
        @SerialName("lon")
        val lon: Double? = 30.26,
        @SerialName("name")
        val name: String? = "Saint Petersburg",
        @SerialName("region")
        val region: String? = "",
        @SerialName("tz_id")
        val tzId: String? = ""
    )
}