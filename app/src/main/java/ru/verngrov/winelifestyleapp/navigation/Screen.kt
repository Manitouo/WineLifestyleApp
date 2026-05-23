package ru.verngrov.winelifestyleapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Wineries : Screen("wineries")
    object WineryDetail : Screen("winery_detail/{wineryId}") {
        fun createRoute(wineryId: Int) = "winery_detail/$wineryId"
    }

    object Map : Screen("map")
    object Articles : Screen("article")
    object ArticleDetail : Screen("article_detail/{articleId}") {
        fun createRoute(articleId: Int) = "article_detail/$articleId"
    }

    object Diary : Screen("diary")
    object DiaryNewEntry : Screen("diary_new_entry")
}