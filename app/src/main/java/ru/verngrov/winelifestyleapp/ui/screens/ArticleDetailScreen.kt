package ru.verngrov.winelifestyleapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.verngrov.winelifestyleapp.R
import ru.verngrov.winelifestyleapp.ui.components.TagChip
import ru.verngrov.winelifestyleapp.ui.theme.*
import ru.verngrov.winelifestyleapp.viewmodel.ArticleViewModel

@Composable
fun ArticleDetailScreen(
    articleId: Int,
    onBack: () -> Unit,
    viewModel: ArticleViewModel = viewModel()
) {
    val article = viewModel.getArticleById(articleId) ?: return
    val savedIds by viewModel.savedArticleIds.collectAsState()
    val isSaved = savedIds.contains(articleId)

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(BackgroundSurface)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_wine_glass),
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.Center)
            )
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = BackgroundCard.copy(alpha = 0.85f),
                modifier = Modifier
                    .padding(12.dp)
                    .size(32.dp)
                    .align(Alignment.TopStart)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Назад",
                            tint = TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = BackgroundCard.copy(alpha = 0.85f),
                modifier = Modifier
                    .padding(12.dp)
                    .size(32.dp)
                    .align(Alignment.TopEnd)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    IconButton(onClick = { viewModel.toggleSaved(articleId) }) {
                        Icon(
                            painter = painterResource(
                                if (isSaved) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                            ),
                            contentDescription = "Сохранить",
                            tint = if (isSaved) Accent else TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            TagChip(text = article.category)
            Spacer(Modifier.height(8.dp))
            Text(article.title, style = Typography.headlineMedium, color = TextPrimary)
            Spacer(Modifier.height(4.dp))
            Text(
                "${article.readTimeMinutes} мин · ${article.level}",
                style = Typography.labelMedium,
                color = TextTertiary
            )
            Spacer(Modifier.height(16.dp))

            article.content.forEach { section ->
                Text(section.title, style = Typography.titleMedium, color = Accent)
                Spacer(Modifier.height(4.dp))
                Text(section.text, style = Typography.bodyMedium, color = TextSecondary)
                Spacer(Modifier.height(14.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun ArticleDetailScreenPreview() {
    WineLifestyleAppTheme {
        ArticleDetailScreen(articleId = 1, onBack = {})
    }
}