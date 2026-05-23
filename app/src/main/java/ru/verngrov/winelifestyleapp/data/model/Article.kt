package ru.verngrov.winelifestyleapp.data.model

data class Article(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val readTimeMinutes: Int,
    val level: String,
    val content: List<ArticleSection>
)

data class ArticleSection(
    val title: String,
    val text: String
)