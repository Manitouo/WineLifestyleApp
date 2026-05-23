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
import ru.verngrov.winelifestyleapp.ui.components.*
import ru.verngrov.winelifestyleapp.ui.theme.*
import ru.verngrov.winelifestyleapp.viewmodel.ArticleViewModel
import ru.verngrov.winelifestyleapp.viewmodel.WineryViewModel

@Composable
fun HomeScreen(
    onWineryClick: (Int) -> Unit,
    onArticleClick: (Int) -> Unit,
    onSeeAllWineries: () -> Unit,
    onSeeAllArticles: () -> Unit,
    wineryViewModel: WineryViewModel = viewModel(),
    articleViewModel: ArticleViewModel = viewModel()
) {
    val wineries = wineryViewModel.getAllWineries()
    val articles = articleViewModel.getFilteredArticles()
    val savedIds by articleViewModel.savedArticleIds.collectAsState()
    val featured = wineries.firstOrNull()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item { Spacer(Modifier.height(16.dp)) }

        // Hero карточка
        if (featured != null) {
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = DarkBrown,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { onWineryClick(featured.id) }
                ) {
                    Box {
                        Icon(
                            painter = painterResource(R.drawable.ic_wineries),
                            contentDescription = null,
                            tint = DarkBrownDeep,
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.Center)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                        listOf(
                                            androidx.compose.ui.graphics.Color.Transparent,
                                            DarkBrown.copy(alpha = 0.95f)
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Accent
                            ) {
                                Text(
                                    "Рекомендуем",
                                    style = Typography.labelSmall,
                                    color = BackgroundCard,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Spacer(Modifier.height(5.dp))
                            Text(
                                featured.name,
                                style = Typography.headlineMedium,
                                color = BackgroundScreen
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                "${featured.region} · ${featured.wineTypes.firstOrNull() ?: ""}",
                                style = Typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }
                }
                Spacer(Modifier.height(18.dp))
            }
        }

        // Винодельни
        item {
            SectionHeader(title = "Популярные винодельни", onSeeAll = onSeeAllWineries)
            Spacer(Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(wineries.take(5)) { winery ->
                    WineryMiniCard(winery = winery, onClick = { onWineryClick(winery.id) })
                }
            }
            Spacer(Modifier.height(18.dp))
        }

        // Статьи
        item {
            SectionHeader(title = "Статьи", onSeeAll = onSeeAllArticles)
            Spacer(Modifier.height(10.dp))
        }

        items(articles.take(3)) { article ->
            ArticleCardSmall(
                article = article,
                isSaved = savedIds.contains(article.id),
                onSaveToggle = { articleViewModel.toggleSaved(article.id) },
                onClick = { onArticleClick(article.id) }
            )
            Spacer(Modifier.height(8.dp))
        }

        item { Spacer(Modifier.height(8.dp)) }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun HomeScreenPreview() {
    WineLifestyleAppTheme {
        HomeScreen(
            onWineryClick = {},
            onArticleClick = {},
            onSeeAllWineries = {},
            onSeeAllArticles = {}
        )
    }
}