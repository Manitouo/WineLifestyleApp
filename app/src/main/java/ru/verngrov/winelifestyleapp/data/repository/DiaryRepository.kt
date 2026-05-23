package ru.verngrov.winelifestyleapp.data.repository

import kotlinx.coroutines.flow.Flow
import ru.verngrov.winelifestyleapp.data.db.DiaryDao
import ru.verngrov.winelifestyleapp.data.model.DiaryEntry

class DiaryRepository(private val dao: DiaryDao) {
    fun getAllEntries(): Flow<List<DiaryEntry>> = dao.getAllEntries()
    suspend fun insertEntry(entry: DiaryEntry) = dao.insertEntry(entry)
    suspend fun updateEntry(entry: DiaryEntry) = dao.updateEntry(entry)
    suspend fun deleteEntry(entry: DiaryEntry) = dao.deleteEntry(entry)
}