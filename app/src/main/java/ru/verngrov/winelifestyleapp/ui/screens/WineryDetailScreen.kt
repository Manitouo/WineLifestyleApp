package ru.verngrov.winelifestyleapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import ru.verngrov.winelifestyleapp.viewmodel.WineryViewModel

@Composable
fun WineryDetailScreen(
    wineryId: Int,
    onBack: () -> Unit,
    onAddToDiary: (String) -> Unit,
    viewModel: WineryViewModel = viewModel()
) {
    val winery = viewModel.getWineryById(wineryId) ?: return
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("История", "Терруар", "Вина", "Гостям")

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {

        // Hero
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(DarkBrown)
        ) {
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
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = BackgroundCard.copy(alpha = 0.15f),
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
                            tint = BackgroundScreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(14.dp)
            ) {
                Text(
                    winery.slogan,
                    style = Typography.labelMedium,
                    color = TextTertiary
                )
                Spacer(Modifier.height(3.dp))
                Text(winery.name, style = Typography.headlineMedium, color = BackgroundScreen)
                Spacer(Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(winery.region, style = Typography.bodySmall, color = TextSecondary)
                }
            }
        }

        // Статы
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "${winery.foundedYear}" to "Основана",
                winery.hectares to "Га",
                "${2025 - winery.foundedYear}" to "Лет истории"
            ).forEach { (value, label) ->
                Surface(
                    shape = RoundedCornerShape(9.dp),
                    color = BackgroundSurface,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(value, style = Typography.titleMedium, color = TextPrimary)
                        Spacer(Modifier.height(2.dp))
                        Text(label, style = Typography.labelMedium, color = TextSecondary)
                    }
                }
            }
        }

        // Вкладки
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .background(BackgroundSurface, RoundedCornerShape(10.dp))
                .padding(3.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (selectedTab == index) BackgroundCard else androidx.compose.ui.graphics.Color.Transparent,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedTab = index }
                ) {
                    Text(
                        tab,
                        style = Typography.labelSmall,
                        color = if (selectedTab == index) Accent else TextSecondary,
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Контент вкладок
        Column(modifier = Modifier.padding(horizontal = 14.dp)) {
            when (selectedTab) {
                0 -> {
                    // История
                    winery.history.forEach { item ->
                        Row(
                            modifier = Modifier.padding(bottom = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(shape = RoundedCornerShape(6.dp), color = DarkBrown) {
                                Text(
                                    item.year,
                                    style = Typography.labelSmall,
                                    color = TextTertiary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                            Text(item.text, style = Typography.bodySmall, color = TextSecondary)
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = AccentLight,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_tip),
                                contentDescription = null,
                                tint = Accent,
                                modifier = Modifier.size(16.dp)
                            )
                            Column {
                                Text("Знаете ли вы?", style = Typography.labelSmall, color = Accent)
                                Spacer(Modifier.height(3.dp))
                                Text(
                                    winery.funFact,
                                    style = Typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }

                1 -> {
                    // Терруар
                    listOf(
                        R.drawable.ic_mountain to "Высота" to winery.terroir.altitude,
                        R.drawable.ic_leaf to "Почвы" to winery.terroir.soils,
                        R.drawable.ic_location to "Климат" to winery.terroir.climate,
                        R.drawable.ic_grape to "Площадь" to winery.terroir.area
                    ).forEach { (iconLabel, value) ->
                        val (icon, label) = iconLabel
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = BackgroundCard,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    painter = painterResource(icon),
                                    contentDescription = null,
                                    tint = TextTertiary,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(top = 1.dp)
                                )
                                Column {
                                    Text(label, style = Typography.labelSmall, color = TextPrimary)
                                    Spacer(Modifier.height(2.dp))
                                    Text(value, style = Typography.bodySmall, color = TextSecondary)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        winery.terroir.description,
                        style = Typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(10.dp))
                    Text("Сорта винограда", style = Typography.titleMedium, color = TextPrimary)
                    Spacer(Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        items(winery.grapeVarieties) { grape ->
                            TagChip(text = grape, filled = false)
                        }
                    }
                }

                2 -> {
                    // Вина
                    winery.wines.chunked(2).forEach { row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            row.forEach { wine ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = BackgroundCard,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            wine.name,
                                            style = Typography.labelSmall,
                                            color = TextPrimary
                                        )
                                        Spacer(Modifier.height(3.dp))
                                        Text(
                                            wine.description,
                                            style = Typography.bodySmall,
                                            color = TextSecondary
                                        )
                                    }
                                }
                            }
                            if (row.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                }

                3 -> {
                    // Гостям
                    winery.guestInfo.forEach { item ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = BackgroundCard,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_location),
                                    contentDescription = null,
                                    tint = Accent,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(top = 1.dp)
                                )
                                Column {
                                    Text(
                                        item.title,
                                        style = Typography.labelSmall,
                                        color = TextPrimary
                                    )
                                    Spacer(Modifier.height(3.dp))
                                    Text(
                                        item.description,
                                        style = Typography.bodySmall,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    listOf(
                        R.drawable.ic_phone to winery.phone,
                        R.drawable.ic_website to winery.website,
                        R.drawable.ic_clock to winery.hours,
                        R.drawable.ic_location to winery.address
                    ).filter { it.second.isNotBlank() }.forEach { (icon, text) ->
                        Row(
                            modifier = Modifier.padding(bottom = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = null,
                                tint = TextTertiary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(text, style = Typography.bodySmall, color = TextSecondary)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        Button(
            onClick = { onAddToDiary(winery.name) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Accent)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_diary),
                contentDescription = null,
                tint = BackgroundCard,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text("Добавить в дневник", color = BackgroundCard, style = Typography.titleMedium)
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun WineryDetailScreenPreview() {
    WineLifestyleAppTheme {
        WineryDetailScreen(wineryId = 1, onBack = {}, onAddToDiary = {})
    }
}