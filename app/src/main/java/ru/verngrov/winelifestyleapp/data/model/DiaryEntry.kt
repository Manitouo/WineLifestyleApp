package ru.verngrov.winelifestyleapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_entries")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val wineName: String,
    val wineryName: String,
    val date: String,
    val rating: Int,
    val comment: String
)