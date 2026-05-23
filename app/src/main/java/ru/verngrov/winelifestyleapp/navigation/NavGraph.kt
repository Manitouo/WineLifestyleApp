package ru.verngrov.winelifestyleapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.verngrov.winelifestyleapp.ui.screens.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = listOf(
        Screen.Home.route,
        Screen.Wineries.route,
        Screen.Map.route,
        Screen.Articles.route,
        Screen.Diary.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(150))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(150))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onWineryClick = { id ->
                        navController.navigate(Screen.WineryDetail.createRoute(id))
                    },
                    onArticleClick = { id ->
                        navController.navigate(Screen.ArticleDetail.createRoute(id))
                    },
                    onSeeAllWineries = { navController.navigate(Screen.Wineries.route) },
                    onSeeAllArticles = { navController.navigate(Screen.Articles.route) }
                )
            }
            composable(Screen.Wineries.route) {
                WineriesScreen(
                    onWineryClick = { id ->
                        navController.navigate(Screen.WineryDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.WineryDetail.route) { backStackEntry ->
                val wineryId = backStackEntry.arguments?.getString("wineryId")?.toIntOrNull() ?: 0
                WineryDetailScreen(
                    wineryId = wineryId,
                    onBack = { navController.popBackStack() },
                    onAddToDiary = { wineryName ->
                        navController.navigate(Screen.DiaryNewEntry.route + "?wineryName=$wineryName")
                    }
                )
            }
            composable(Screen.Map.route) {
                MapScreen(
                    onWineryClick = { id ->
                        navController.navigate(Screen.WineryDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.Articles.route) {
                ArticlesScreen(
                    onArticleClick = { id ->
                        navController.navigate(Screen.ArticleDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.ArticleDetail.route) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId")?.toIntOrNull() ?: 0
                ArticleDetailScreen(
                    articleId = articleId,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Diary.route) {
                DiaryScreen(
                    onNewEntry = { navController.navigate(Screen.DiaryNewEntry.route) },
                    onEditEntry = { entry ->
                        navController.navigate(Screen.DiaryNewEntry.route + "?entryId=${entry.id}")
                    }
                )
            }
            composable(Screen.DiaryNewEntry.route + "?wineryName={wineryName}&entryId={entryId}") { backStackEntry ->
                val wineryName = backStackEntry.arguments?.getString("wineryName") ?: ""
                DiaryNewEntryScreen(
                    onBack = { navController.popBackStack() },
                    prefillWineryName = wineryName
                )
            }
        }
    }
}