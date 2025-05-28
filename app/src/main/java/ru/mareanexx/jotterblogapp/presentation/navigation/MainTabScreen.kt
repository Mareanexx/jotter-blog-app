package ru.mareanexx.jotterblogapp.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.mareanexx.common.ui.bottombar.BottomNavBar
import ru.mareanexx.common.ui.bottombar.Tabs.Collections
import ru.mareanexx.common.ui.bottombar.Tabs.CreateArticle
import ru.mareanexx.common.ui.bottombar.Tabs.Home
import ru.mareanexx.common.ui.bottombar.Tabs.Notifications
import ru.mareanexx.common.ui.bottombar.Tabs.Settings
import ru.mareanexx.feature_articles.presentation.screens.create_article.AddArticleContentScreen
import ru.mareanexx.feature_articles.presentation.screens.create_article.CreateArticleRoute
import ru.mareanexx.feature_articles.presentation.screens.create_article.CreateArticleScreen
import ru.mareanexx.feature_articles.presentation.screens.create_article.viewmodel.CreateArticleViewModel
import ru.mareanexx.feature_collections.presentation.screens.collection.ConcreteCollectionScreen
import ru.mareanexx.feature_collections.presentation.screens.list.CollectionsRoute
import ru.mareanexx.feature_collections.presentation.screens.list.CollectionsScreen
import ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.CollectionsViewModel
import ru.mareanexx.feature_settings.presentation.screens.other.OtherSettingsScreen
import ru.mareanexx.feature_settings.presentation.screens.profile.ProfileSettingsScreen
import ru.mareanexx.feature_settings.presentation.screens.settings.SettingsRoute
import ru.mareanexx.feature_settings.presentation.screens.settings.SettingsScreen


@Composable
fun MainTabScreen(navController: NavHostController) {
    val navItems = listOf(Home, Collections, Notifications, Settings)

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val selectedTab = navItems.find { it.route == currentRoute } ?: Home

    val showBottomNavBar = currentRoute in navItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomNavBar) {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    onAddArticleButtonClicked = { navController.navigate(CreateArticle.route) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            composable(Home.route) {
                // HomeScreen()
            }

            navigation(
                startDestination = CreateArticle.route,
                route = "create_article_graph"
            ) {
                composable(
                    route = CreateArticle.route,
                    enterTransition = { slideInVertically(initialOffsetY = { it }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                ) { entry ->
                    val parentEntry = remember(entry) { navController.getBackStackEntry(CreateArticle.route) }
                    val viewModel: CreateArticleViewModel = hiltViewModel(parentEntry)

                    CreateArticleScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToNextPage = { navController.navigate(CreateArticleRoute.AddContent.route) },
                        viewModel = viewModel
                    )
                }

                composable(
                    route = CreateArticleRoute.AddContent.route,
                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                    exitTransition = { slideOutVertically(targetOffsetY = { it }) }
                ) { entry ->
                    val parentEntry = remember(entry) { navController.getBackStackEntry(CreateArticle.route) }
                    val viewModel: CreateArticleViewModel = hiltViewModel(parentEntry)

                    AddArticleContentScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onCloseClicked = { navController.popBackStack(route = Home.route, inclusive = false) },
                        viewModel = viewModel
                    )
                }
            }

            navigation(
                startDestination = Collections.route,
                route = "collections_graph"
            ) {
                composable(
                    route = Collections.route,
                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                ) { entry ->
                    val parentEntry = remember(entry) { navController.getBackStackEntry(Collections.route) }
                    val viewModel: CollectionsViewModel = hiltViewModel(parentEntry)

                    CollectionsScreen(
                        onNavigateToConcreteCollection = { id, name ->
                            navController.navigate("${CollectionsRoute.CollectionArticles.route}/$id/$name")
                        },
                        collectionsViewModel = viewModel
                    )
                }

                composable(
                    route = "${CollectionsRoute.CollectionArticles.route}/{id}/{name}",
                    arguments = listOf(
                        navArgument("id") { type = NavType.IntType },
                        navArgument("name") { type = NavType.StringType }
                    )
                ) { entry ->
                    val parentEntry = remember(entry) { navController.getBackStackEntry(Collections.route) }
                    val viewModel: CollectionsViewModel = hiltViewModel(parentEntry)
                    val id = entry.arguments?.getInt("id") ?: 0
                    val name = entry.arguments?.getString("name") ?: ""

                    ConcreteCollectionScreen(
                        collectionName = name,
                        collectionId = id,
                        onNavigateBack = { navController.popBackStack() },
                        collectionsViewModel = viewModel
                    )
                }
            }

            composable(Notifications.route) {
                // NotificationsScreen()
            }

            composable(
                route = Settings.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
            ) {
                SettingsScreen { route -> navController.navigate(route.route) }
            }
            composable(SettingsRoute.Profile.route) {
                ProfileSettingsScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable(SettingsRoute.Others.route) {
                OtherSettingsScreen(onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}