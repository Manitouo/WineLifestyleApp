package ru.verngrov.winelifestyleapp.ui.screens

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
import ru.verngrov.winelifestyleapp.data.model.DiaryEntry
import ru.verngrov.winelifestyleapp.ui.components.ScoreSelector
import ru.verngrov.winelifestyleapp.ui.theme.*
import ru.verngrov.winelifestyleapp.viewmodel.DiaryViewModel
import ru.verngrov.winelifestyleapp.viewmodel.WineryViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryNewEntryScreen(
    onBack: () -> Unit,
    prefillWineryName: String = "",
    editEntry: DiaryEntry? = null,
    diaryViewModel: DiaryViewModel = viewModel(),
    wineryViewModel: WineryViewModel = viewModel()
) {
    var wineName by remember { mutableStateOf(editEntry?.wineName ?: "") }
    var wineryName by remember { mutableStateOf(editEntry?.wineryName ?: prefillWineryName) }
    var date by remember {
        mutableStateOf(
            editEntry?.date ?: LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
        )
    }
    var rating by remember { mutableStateOf(editEntry?.rating ?: 5) }
    var comment by remember { mutableStateOf(editEntry?.comment ?: "") }
    val wineries = wineryViewModel.getAllWineries()
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = BackgroundSurface,
                    modifier = Modifier.size(32.dp)
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
                Text(
                    if (editEntry == null) "Новая запись" else "Редактировать",
                    style = Typography.headlineMedium,
                    color = TextPrimary
                )
            }
            TextButton(onClick = {
                val entry = DiaryEntry(
                    id = editEntry?.id ?: 0,
                    wineName = wineName,
                    wineryName = wineryName,
                    date = date,
                    rating = rating,
                    comment = comment
                )
                if (editEntry == null) diaryViewModel.addEntry(entry)
                else diaryViewModel.updateEntry(entry)
                onBack()
            }) {
                Text("Сохранить", color = Accent, style = Typography.titleMedium)
            }
        }

        Spacer(Modifier.height(16.dp))

        FormLabel("Название вина")
        OutlinedTextField(
            value = wineName,
            onValueChange = { wineName = it },
            modifier = Modifier.fillMaxWidth(),
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

        FormLabel("Винодельня")
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(
                value = wineryName,
                onValueChange = { wineryName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Border,
                    focusedBorderColor = Accent,
                    unfocusedContainerColor = BackgroundCard,
                    focusedContainerColor = BackgroundCard
                ),
                textStyle = Typography.bodyMedium.copy(color = TextPrimary),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_down),
                        contentDescription = null,
                        tint = TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                },
                singleLine = true
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                wineries.forEach { winery ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                winery.name,
                                style = Typography.bodyMedium,
                                color = TextPrimary
                            )
                        },
                        onClick = { wineryName = winery.name; expanded = false }
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        FormLabel("Дата")
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Border,
                focusedBorderColor = Accent,
                unfocusedContainerColor = BackgroundCard,
                focusedContainerColor = BackgroundCard
            ),
            textStyle = Typography.bodyMedium.copy(color = TextPrimary),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(16.dp)
                )
            },
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormLabel("Оценка")
            Surface(shape = RoundedCornerShape(6.dp), color = AccentLight) {
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
                    Text("$rating", style = Typography.titleMedium, color = Accent)
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        ScoreSelector(selected = rating, onSelect = { rating = it })

        Spacer(Modifier.height(10.dp))

        FormLabel("Комментарий")
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 80.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Border,
                focusedBorderColor = Accent,
                unfocusedContainerColor = BackgroundCard,
                focusedContainerColor = BackgroundCard
            ),
            textStyle = Typography.bodyMedium.copy(color = TextPrimary),
            maxLines = 5
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(
        text = text,
        style = Typography.labelMedium,
        color = TextSecondary,
        modifier = Modifier.padding(bottom = 5.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun DiaryNewEntryScreenPreview() {
    WineLifestyleAppTheme {
        DiaryNewEntryScreen(onBack = {})
    }
}