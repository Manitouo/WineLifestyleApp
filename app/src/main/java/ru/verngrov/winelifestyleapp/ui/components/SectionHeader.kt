package ru.verngrov.winelifestyleapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.verngrov.winelifestyleapp.ui.theme.*

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun SectionHeaderPreview() {
    WineLifestyleAppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader(title = "Популярные винодельни", onSeeAll = {})
            Spacer(Modifier.height(8.dp))
            SectionHeader(title = "Без кнопки")
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = Typography.titleMedium, color = TextPrimary)
        if (onSeeAll != null) {
            Text(
                text = "Все →",
                style = Typography.labelMedium,
                color = Accent,
                modifier = Modifier.clickable { onSeeAll() }
            )
        }
    }
}