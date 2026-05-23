package ru.verngrov.winelifestyleapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.verngrov.winelifestyleapp.ui.theme.*

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun ScoreSelectorPreview() {
    WineLifestyleAppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ScoreSelector(selected = 7, onSelect = {})
        }
    }
}

@Composable
fun ScoreSelector(selected: Int, onSelect: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        (1..10).forEach { score ->
            val isSelected = score == selected
            Surface(
                shape = RoundedCornerShape(4.dp),
                color = if (isSelected) Accent else BackgroundCard,
                modifier = Modifier
                    .weight(1f)
                    .height(32.dp)
                    .then(
                        if (!isSelected) Modifier.border(0.5.dp, Border, RoundedCornerShape(4.dp))
                        else Modifier
                    )
                    .clickable { onSelect(score) }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "$score",
                        style = Typography.labelSmall,
                        color = if (isSelected) BackgroundCard else TextSecondary
                    )
                }
            }
        }
    }
}