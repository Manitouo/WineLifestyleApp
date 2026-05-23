package ru.verngrov.winelifestyleapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.verngrov.winelifestyleapp.data.db.AppDatabase
import ru.verngrov.winelifestyleapp.data.model.DiaryEntry
import ru.verngrov.winelifestyleapp.data.repository.DiaryRepository

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DiaryRepository(
        AppDatabase.getDatabase(application).diaryDao()
    )

    val entries = repository.getAllEntries().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addEntry(entry: DiaryEntry) = viewModelScope.launch {
        repository.insertEntry(entry)
    }

    fun updateEntry(entry: DiaryEntry) = viewModelScope.launch {
        repository.updateEntry(entry)
    }

    fun deleteEntry(entry: DiaryEntry) = viewModelScope.launch {
        repository.deleteEntry(entry)
    }
}