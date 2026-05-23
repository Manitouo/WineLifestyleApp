package ru.verngrov.winelifestyleapp.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.verngrov.winelifestyleapp.data.model.DiaryEntry

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_entries ORDER BY id DESC")
    fun getAllEntries(): Flow<List<DiaryEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: DiaryEntry)

    @Update
    suspend fun updateEntry(entry: DiaryEntry)

    @Delete
    suspend fun deleteEntry(entry: DiaryEntry)
}