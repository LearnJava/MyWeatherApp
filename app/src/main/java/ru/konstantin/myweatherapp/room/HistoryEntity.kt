package ru.konstantin.myweatherapp.room

import androidx.room.*

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Double,
    val condition: String
)
