package ru.verngrov.winelifestyleapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import ru.verngrov.winelifestyleapp.data.model.DiaryEntry
import ru.verngrov.winelifestyleapp.ui.components.DiaryEntryCard
import ru.verngrov.winelifestyleapp.ui.theme.*
import ru.verngrov.winelifestyleapp.viewmodel.DiaryViewModel

@Composable
fun DiaryScreen(
    onNewEntry: () -> Unit,
    onEditEntry: (DiaryEntry) -> Unit,
    viewModel: DiaryViewModel = viewModel()
) {
    val entries by viewModel.entries.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Дневник", style = Typography.headlineMedium, color = TextPrimary)
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Accent,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    IconButton(onClick = onNewEntry) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Добавить",
                            tint = BackgroundCard,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Статистика
        if (entries.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatBox(
                    label = "записей",
                    value = "${entries.size}",
                    modifier = Modifier.weight(1f)
                )
                StatBox(
                    label = "ср. оценка",
                    value = "%.1f".format(entries.map { it.rating }.average()),
                    modifier = Modifier.weight(1f)
                )
                StatBox(
                    label = "виноделен",
                    value = "${entries.map { it.wineryName }.distinct().size}",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(14.dp))
        }

        if (entries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(R.drawable.ic_diary),
                        contentDescription = null,
                        tint = TextTertiary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Дневник пуст", style = Typography.titleMedium, color = TextSecondary)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Добавьте первую запись дегустации",
                        style = Typography.bodySmall,
                        color = TextTertiary
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(entries, key = { it.id }) { entry ->
                    DiaryEntryCard(
                        entry = entry,
                        onEdit = { onEditEntry(entry) },
                        onDelete = { viewModel.deleteEntry(entry) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = AccentLight,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = Typography.headlineMedium, color = TextPrimary)
            Spacer(Modifier.height(2.dp))
            Text(label, style = Typography.labelMedium, color = TextSecondary)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun DiaryScreenPreview() {
    WineLifestyleAppTheme {
        DiaryScreen(onNewEntry = {}, onEditEntry = {})
    }
}