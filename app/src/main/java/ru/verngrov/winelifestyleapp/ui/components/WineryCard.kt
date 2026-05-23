package ru.verngrov.winelifestyleapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import ru.verngrov.winelifestyleapp.data.model.TerroirInfo
import ru.verngrov.winelifestyleapp.data.model.Winery
import ru.verngrov.winelifestyleapp.ui.theme.*

private val previewWinery = Winery(
    id = 1, name = "Абрау-Дюрсо", slogan = "Там, где рождается русское шампанское",
    region = "Новороссийск", subregion = "Новороссийск", address = "ул. Промышленная, 19",
    foundedYear = 1870, hectares = "3700+", description = "", phone = "", website = "",
    hours = "", wineTypes = listOf("игристое", "белое"), grapeVarieties = emptyList(),
    wines = emptyList(), history = emptyList(),
    terroir = TerroirInfo("", "", "", "", ""),
    guestInfo = emptyList(), funFact = "", latitude = 44.7, longitude = 37.5
)

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun WineryCardPreview() {
    WineLifestyleAppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WineryCard(winery = previewWinery, onClick = {})
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun WineryMiniCardPreview() {
    WineLifestyleAppTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WineryMiniCard(winery = previewWinery, onClick = {})
            WineryMiniCard(
                winery = previewWinery.copy(name = "Фанагория", region = "Тамань"),
                onClick = {})
        }
    }
}

@Composable
fun WineryCard(winery: Winery, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = BackgroundCard,
        tonalElevation = 0.dp,
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
                    painter = painterResource(R.drawable.ic_wineries),
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(winery.name, style = Typography.titleMedium, color = TextPrimary)
                Text(winery.region, style = Typography.bodySmall, color = TextSecondary)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(winery.wineTypes.take(2)) { type ->
                        TagChip(text = type)
                    }
                }
            }
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )
        }
    }
}

@Composable
fun WineryMiniCard(winery: Winery, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = BackgroundCard,
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(BackgroundSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_wineries),
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(winery.name, style = Typography.labelSmall, color = TextPrimary, maxLines = 1)
                Text(winery.region, style = Typography.labelMedium, color = TextSecondary)
            }
        }
    }
}