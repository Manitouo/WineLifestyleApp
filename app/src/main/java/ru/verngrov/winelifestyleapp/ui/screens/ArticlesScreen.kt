package ru.verngrov.winelifestyleapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.verngrov.winelifestyleapp.R
import ru.verngrov.winelifestyleapp.ui.components.ArticleCardLarge
import ru.verngrov.winelifestyleapp.ui.components.ArticleCardSmall
import ru.verngrov.winelifestyleapp.ui.theme.*
import ru.verngrov.winelifestyleapp.viewmodel.ArticleViewModel

@Composable
fun ArticlesScreen(
    onArticleClick: (Int) -> Unit,
    viewModel: ArticleViewModel = viewModel()
) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val savedIds by viewModel.savedArticleIds.collectAsState()
    var showSaved by remember { mutableStateOf(false) }
    val categories = listOf("Все", "Сорта", "Дегустация", "История")
    val articles = if (showSaved) viewModel.getSavedArticles() else viewModel.getFilteredArticles()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Text("Статьи", style = Typography.headlineMedium, color = TextPrimary)
        Spacer(Modifier.height(12.dp))

        // Сегмент Все / Сохранённые
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundSurface, RoundedCornerShape(10.dp))
                .padding(3.dp)
        ) {
            listOf(false to "Все", true to "Сохранённые").forEach { (isSaved, label) ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (showSaved == isSaved) BackgroundCard else androidx.compose.ui.graphics.Color.Transparent,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showSaved = isSaved }
                ) {
                    Text(
                        label,
                        style = Typography.labelSmall,
                        color = if (showSaved == isSaved) Accent else TextSecondary,
                        modifier = Modifier
                            .padding(vertical = 7.dp)
                            .wrapContentWidth(androidx.compose.ui.Alignment.CenterHorizontally)
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        if (!showSaved) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(categories) { category ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (selectedCategory == category) Accent else BackgroundCard,
                        modifier = Modifier.clickable { viewModel.setCategory(category) }
                    ) {
                        Text(
                            category,
                            style = Typography.labelSmall,
                            color = if (selectedCategory == category) BackgroundCard else TextSecondary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(articles.take(1)) { article ->
                ArticleCardLarge(
                    article = article,
                    isSaved = savedIds.contains(article.id),
                    onSaveToggle = { viewModel.toggleSaved(article.id) },
                    onClick = { onArticleClick(article.id) }
                )
            }
            items(articles.drop(1), key = { it.id }) { article ->
                ArticleCardSmall(
                    article = article,
                    isSaved = savedIds.contains(article.id),
                    onSaveToggle = { viewModel.toggleSaved(article.id) },
                    onClick = { onArticleClick(article.id) }
                )
            }
        }

        if (articles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(R.drawable.ic_bookmark),
                        contentDescription = null,
                        tint = TextTertiary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        if (showSaved) "Нет сохранённых статей" else "Статьи не найдены",
                        style = Typography.titleMedium,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun ArticlesScreenPreview() {
    WineLifestyleAppTheme {
        ArticlesScreen(onArticleClick = {})
    }
}