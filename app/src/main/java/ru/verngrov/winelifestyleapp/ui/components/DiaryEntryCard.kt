package ru.verngrov.winelifestyleapp.ui.components

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
import ru.verngrov.winelifestyleapp.data.model.DiaryEntry
import ru.verngrov.winelifestyleapp.ui.theme.*

private val previewEntry = DiaryEntry(
    id = 1, wineName = "Империал Брют", wineryName = "Абрау-Дюрсо",
    date = "14 мая 2025", rating = 9,
    comment = "Нежные пузырьки, аромат зелёного яблока и цитруса."
)

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun DiaryEntryCardPreview() {
    WineLifestyleAppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DiaryEntryCard(entry = previewEntry, onEdit = {}, onDelete = {})
            DiaryEntryCard(
                entry = previewEntry.copy(
                    wineName = "Фанагория Авторское",
                    wineryName = "Фанагория",
                    rating = 8,
                    comment = ""
                ), onEdit = {}, onDelete = {})
        }
    }
}

@Composable
fun DiaryEntryCard(
    entry: DiaryEntry,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = BackgroundCard,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(entry.wineName, style = Typography.titleMedium, color = TextPrimary)
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "${entry.wineryName} · ${entry.date}",
                        style = Typography.bodySmall,
                        color = TextSecondary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AccentLight
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star),
                            contentDescription = null,
                            tint = Accent,
                            modifier = Modifier.size(12.dp)
                        )
                        Text("${entry.rating}", style = Typography.titleMedium, color = Accent)
                    }
                }
            }
            if (entry.comment.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(
                    entry.comment,
                    style = Typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 2
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                //horizontalArrangement = Arrangement.End,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Редактировать",
                    tint = TextTertiary,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onEdit() }
                )
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Удалить",
                    tint = TextTertiary,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}