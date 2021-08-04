package ru.konstantin.myweatherapp.service

import ru.konstantin.myweatherapp.model.data.GeoCity
import java.io.InputStream

class GeoCityService {

    fun getAllRussianCities(inputStream: InputStream): List<GeoCity> {
        val geoCities = mutableListOf<GeoCity>()

        inputStream.bufferedReader().forEachLine {
            val city = it.trim().replace(",", "").split("\\s".toRegex())
            if (city.count() == 3) {
                geoCities.add(
                    GeoCity(
                        city[0],
                        city[1].toDouble(),
                        city[2].toDouble()
                    )
                )
            }
        }
        return geoCities.sortedBy { it.cityName }
    }
}