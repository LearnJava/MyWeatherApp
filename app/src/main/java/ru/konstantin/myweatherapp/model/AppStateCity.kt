package ru.konstantin.myweatherapp.model

import ru.konstantin.myweatherapp.model.data.GeoCity

sealed class AppStateCity {
    data class Success(val geocityList: List<GeoCity>) : AppStateCity()
    class Error(val error: Throwable) : AppStateCity()
    object Loading : AppStateCity()
}
