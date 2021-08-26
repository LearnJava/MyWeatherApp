package ru.konstantin.myweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.konstantin.myweatherapp.model.AppStateContactList
import ru.konstantin.myweatherapp.model.repository.RepositoryContact
import ru.konstantin.myweatherapp.model.repository.RepositoryContactImpl

class ContactsViewModel(private val repository: RepositoryContact = RepositoryContactImpl()) : ViewModel() {
    val contacts: MutableLiveData<AppStateContactList> = MutableLiveData()

    fun getContacts(){
        contacts.value = AppStateContactList.Loading
        val answer = repository.getListOfContact()
        contacts.value = AppStateContactList.Success(answer)
    }
}