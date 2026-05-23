package ru.verngrov.winelifestyleapp.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.verngrov.winelifestyleapp.R
import ru.verngrov.winelifestyleapp.ui.theme.Accent
import ru.verngrov.winelifestyleapp.ui.theme.BackgroundCard
import ru.verngrov.winelifestyleapp.ui.theme.TextTertiary
import ru.verngrov.winelifestyleapp.ui.theme.WineLifestyleAppTheme

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun BottomNavBarPreview() {
    WineLifestyleAppTheme {
        BottomNavBar(currentRoute = "home", onNavigate = {})
    }
}

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String,
)

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem("Главная", R.drawable.ic_home, Screen.Home.route),
        BottomNavItem("Винодельни", R.drawable.ic_wineries, Screen.Wineries.route),
        BottomNavItem("Карта", R.drawable.ic_map, Screen.Map.route),
        BottomNavItem("Дневник", R.drawable.ic_diary, Screen.Diary.route),
    )

    NavigationBar(
        //modifier = Modifier.fillMaxWidth(),
        containerColor = BackgroundCard,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Accent,
                    selectedTextColor = Accent,
                    unselectedIconColor = TextTertiary,
                    unselectedTextColor = TextTertiary,
                    indicatorColor = BackgroundCard
                ),
            )
        }
    }
}