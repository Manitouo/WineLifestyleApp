package ru.verngrov.winelifestyleapp.ui.screens

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
import ru.verngrov.winelifestyleapp.ui.components.WineryCard
import ru.verngrov.winelifestyleapp.ui.theme.*
import ru.verngrov.winelifestyleapp.viewmodel.WineryViewModel

@Composable
fun WineriesScreen(
    onWineryClick: (Int) -> Unit,
    viewModel: WineryViewModel = viewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val filters = listOf("Все", "Красное", "Белое", "Игристое", "Розовое")
    val wineries by remember(searchQuery, selectedFilter) {
        derivedStateOf { viewModel.getFilteredWineries() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Text("Винодельни", style = Typography.headlineMedium, color = TextPrimary)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Поиск по названию...", style = Typography.bodyMedium, color = TextTertiary)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(18.dp)
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Border,
                focusedBorderColor = Accent,
                unfocusedContainerColor = BackgroundCard,
                focusedContainerColor = BackgroundCard
            ),
            textStyle = Typography.bodyMedium.copy(color = TextPrimary),
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(filters) { filter ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (selectedFilter == filter) Accent else BackgroundCard,
                    modifier = Modifier.clickable { viewModel.setFilter(filter) }
                ) {
                    Text(
                        text = filter,
                        style = Typography.labelSmall,
                        color = if (selectedFilter == filter) BackgroundCard else TextSecondary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        if (wineries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ничего не найдено", style = Typography.bodyMedium, color = TextSecondary)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(wineries, key = { it.id }) { winery ->
                    WineryCard(winery = winery, onClick = { onWineryClick(winery.id) })
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun WineriesScreenPreview() {
    WineLifestyleAppTheme {
        WineriesScreen(onWineryClick = {})
    }
}