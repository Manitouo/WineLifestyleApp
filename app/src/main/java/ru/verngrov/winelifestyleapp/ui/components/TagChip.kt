package ru.verngrov.winelifestyleapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.verngrov.winelifestyleapp.ui.theme.*

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun TagChipPreview() {
    WineLifestyleAppTheme {
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            TagChip(text = "игристое")
            TagChip(text = "красное", filled = false)
        }
    }
}

@Composable
fun TagChip(text: String, filled: Boolean = true) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (filled) AccentLight else BackgroundCard,
        modifier = if (!filled) Modifier.border(
            0.5.dp,
            Border,
            RoundedCornerShape(8.dp)
        ) else Modifier
    ) {
        Text(
            text = text,
            style = Typography.labelSmall,
            color = if (filled) Accent else TextSecondary,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}