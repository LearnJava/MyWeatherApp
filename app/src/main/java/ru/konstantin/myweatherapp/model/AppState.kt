package ru.konstantin.myweatherapp.model

import ru.konstantin.myweatherapp.model.data.GeoCity

sealed class AppState {
    data class Success(val geocityList: List<GeoCity>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
