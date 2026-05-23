package ru.verngrov.winelifestyleapp.data.model

data class Winery(
    val id: Int,
    val name: String,
    val slogan: String,
    val region: String,
    val subregion: String,
    val address: String,
    val foundedYear: Int,
    val hectares: String,
    val description: String,
    val phone: String,
    val website: String,
    val hours: String,
    val wineTypes: List<String>,
    val grapeVarieties: List<String>,
    val wines: List<WineItem>,
    val history: List<HistoryItem>,
    val terroir: TerroirInfo,
    val guestInfo: List<GuestItem>,
    val funFact: String,
    val latitude: Double,
    val longitude: Double
)

data class WineItem(
    val name: String,
    val description: String
)

data class HistoryItem(
    val year: String,
    val text: String
)

data class TerroirInfo(
    val soils: String,
    val climate: String,
    val altitude: String,
    val area: String,
    val description: String
)

data class GuestItem(
    val icon: String,
    val title: String,
    val description: String
)