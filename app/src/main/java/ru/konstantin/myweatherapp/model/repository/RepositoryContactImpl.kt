package ru.konstantin.myweatherapp.model.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import ru.konstantin.myweatherapp.app.ContextProvider
import ru.konstantin.myweatherapp.app.IContextProvider

class RepositoryContactImpl(contextProvider: IContextProvider = ContextProvider) :
    RepositoryContact {

    private val contentResolver: ContentResolver = contextProvider.context.contentResolver

    override fun getListOfContact(): List<String> {
        val cursorWithContacts: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        val answer = mutableListOf<String>()

        cursorWithContacts?.let{ cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast){

                if (!cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).isNullOrBlank()) {
                    try {
                        answer.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
                    } catch (e: NullPointerException) {
                        e.message?.let { Log.e("MY_PHONE", it) }
                    }
                }

                cursor.moveToNext()
            }
            cursorWithContacts.close()
        }
        return answer.toSet().toList()
    }
}