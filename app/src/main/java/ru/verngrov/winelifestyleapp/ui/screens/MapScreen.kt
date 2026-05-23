package ru.verngrov.winelifestyleapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import ru.verngrov.winelifestyleapp.R
import ru.verngrov.winelifestyleapp.data.model.Winery
import ru.verngrov.winelifestyleapp.ui.theme.*
import ru.verngrov.winelifestyleapp.viewmodel.WineryViewModel

@Preview(showBackground = true, backgroundColor = 0xFFFAF8F5)
@Composable
fun MapScreenPreview() {
    WineLifestyleAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.ic_map),
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Карта отображается только на устройстве",
                    style = Typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
fun MapScreen(
    onWineryClick: (Int) -> Unit,
    viewModel: WineryViewModel = viewModel()
) {
    val wineries = viewModel.getAllWineries()
    var selectedWinery by remember { mutableStateOf<Winery?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(16.dp))
        Text(
            "Карта",
            style = Typography.headlineMedium,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(10.dp))

        Box(modifier = Modifier.weight(1f)) {
            AndroidView(
                factory = { context ->
                    Configuration.getInstance().userAgentValue = context.packageName
                    MapView(context).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(9.0)
                        controller.setCenter(GeoPoint(44.9, 37.3))
                        wineries.forEach { winery ->
                            val marker = Marker(this)
                            marker.position = GeoPoint(winery.latitude, winery.longitude)
                            marker.title = winery.name
                            marker.icon =
                                ContextCompat.getDrawable(context, R.drawable.ic_map_marker)
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Мини-карточка выбранной винодельни
        selectedWinery?.let { winery ->
            Surface(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = BackgroundCard,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                        .clickable { onWineryClick(winery.id) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = BackgroundSurface,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(R.drawable.ic_wineries),
                                contentDescription = null,
                                tint = TextTertiary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(winery.name, style = Typography.titleMedium, color = TextPrimary)
                        Spacer(Modifier.height(2.dp))
                        Text(
                            "${winery.region} · ${winery.wineTypes.firstOrNull() ?: ""}",
                            style = Typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_right),
                        contentDescription = null,
                        tint = TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
