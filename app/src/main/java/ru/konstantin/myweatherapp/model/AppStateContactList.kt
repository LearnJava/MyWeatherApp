package ru.konstantin.myweatherapp.model

sealed class AppStateContactList {
    data class Success(val contactList: List<String>) : AppStateContactList()
    class Error(val error: Throwable) : AppStateContactList()
    object Loading : AppStateContactList()
}