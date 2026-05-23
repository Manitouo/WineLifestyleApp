package ru.verngrov.winelifestyleapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.verngrov.winelifestyleapp.data.model.Article
import ru.verngrov.winelifestyleapp.data.repository.ArticleRepository

class ArticleViewModel : ViewModel() {
    private val repository = ArticleRepository()

    private val _savedArticleIds = MutableStateFlow<Set<Int>>(emptySet())
    val savedArticleIds = _savedArticleIds.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Все")
    val selectedCategory = _selectedCategory.asStateFlow()

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun toggleSaved(articleId: Int) {
        val current = _savedArticleIds.value.toMutableSet()
        if (current.contains(articleId)) current.remove(articleId)
        else current.add(articleId)
        _savedArticleIds.value = current
    }

    fun isArticleSaved(articleId: Int): Boolean =
        _savedArticleIds.value.contains(articleId)

    fun getFilteredArticles(): List<Article> {
        return repository.getArticles().filter { article ->
            _selectedCategory.value == "Все" || article.category == _selectedCategory.value
        }
    }

    fun getSavedArticles(): List<Article> {
        return repository.getArticles().filter { _savedArticleIds.value.contains(it.id) }
    }

    fun getArticleById(id: Int): Article? = repository.getArticleById(id)
}