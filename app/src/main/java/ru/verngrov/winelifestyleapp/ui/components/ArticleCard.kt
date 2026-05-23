package ru.verngrov.winelifestyleapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.verngrov.winelifestyleapp.R
import ru.verngrov.winelifestyleapp.data.model.Article
import ru.verngrov.winelifestyleapp.ui.theme.*

private val previewArticle = Article(
    id = 1, title = "Как правильно дегустировать вино",
    description = "Пять шагов для начинающих — от цвета до послевкусия",
    category = "Дегустация", readTimeMinutes = 5, level = "Начинающим", content = emptyList()
)

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun ArticleCardLargePreview() {
    WineLifestyleAppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ArticleCardLarge(
                article = previewArticle,
                isSaved = false,
                onSaveToggle = {},
                onClick = {})
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun ArticleCardSmallPreview() {
    WineLifestyleAppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ArticleCardSmall(
                article = previewArticle,
                isSaved = false,
                onSaveToggle = {},
                onClick = {})
            ArticleCardSmall(
                article = previewArticle.copy(title = "История виноделия на Кубани"),
                isSaved = true,
                onSaveToggle = {},
                onClick = {})
        }
    }
}

@Composable
fun ArticleCardLarge(
    article: Article,
    isSaved: Boolean,
    onSaveToggle: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = BackgroundCard,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(BackgroundSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_wine_glass),
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(36.dp)
                )
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TagChip(text = article.category)
                    Icon(
                        painter = painterResource(
                            if (isSaved) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                        ),
                        contentDescription = "Сохранить",
                        tint = if (isSaved) Accent else TextTertiary,
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { onSaveToggle() }
                    )
                }
                Spacer(Modifier.height(5.dp))
                Text(article.title, style = Typography.titleMedium, color = TextPrimary)
                Spacer(Modifier.height(2.dp))
                Text(article.description, style = Typography.bodySmall, color = TextSecondary)
                Spacer(Modifier.height(4.dp))
                Text(
                    "${article.readTimeMinutes} мин · ${article.level}",
                    style = Typography.labelMedium,
                    color = TextTertiary
                )
            }
        }
    }
}

@Composable
fun ArticleCardSmall(
    article: Article,
    isSaved: Boolean,
    onSaveToggle: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = BackgroundCard,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.height(72.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(72.dp)
                    .fillMaxHeight()
                    .background(BackgroundSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_wine_glass),
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    article.title,
                    style = Typography.labelSmall,
                    color = TextPrimary,
                    maxLines = 2
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    "${article.readTimeMinutes} мин · ${article.category}",
                    style = Typography.labelMedium,
                    color = TextSecondary
                )
            }
            Icon(
                painter = painterResource(
                    if (isSaved) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                ),
                contentDescription = "Сохранить",
                tint = if (isSaved) Accent else TextTertiary,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(18.dp)
                    .clickable { onSaveToggle() }
            )
        }
    }
}