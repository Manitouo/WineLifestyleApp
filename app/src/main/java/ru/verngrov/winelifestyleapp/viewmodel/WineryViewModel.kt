package ru.verngrov.winelifestyleapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.verngrov.winelifestyleapp.data.model.Winery
import ru.verngrov.winelifestyleapp.data.repository.WineryRepository

class WineryViewModel : ViewModel() {
    private val repository = WineryRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow("Все")
    val selectedFilter = _selectedFilter.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun getFilteredWineries(): List<Winery> {
        return repository.getWineries().filter { winery ->
            val matchesSearch = winery.name.contains(_searchQuery.value, ignoreCase = true) ||
                    winery.region.contains(_searchQuery.value, ignoreCase = true)
            val matchesFilter = _selectedFilter.value == "Все" ||
                    winery.wineTypes.any {
                        it.contains(_selectedFilter.value.lowercase(), ignoreCase = true)
                    }
            matchesSearch && matchesFilter
        }
    }

    fun getWineryById(id: Int): Winery? = repository.getWineryById(id)

    fun getAllWineries(): List<Winery> = repository.getWineries()
}