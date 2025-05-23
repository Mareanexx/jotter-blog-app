package ru.mareanexx.jotterblogapp.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mareanexx.common.ui.bottombar.BottomNavBar
import ru.mareanexx.common.ui.bottombar.Tabs
import ru.mareanexx.common.ui.bottombar.Tabs.Collections
import ru.mareanexx.common.ui.bottombar.Tabs.Home
import ru.mareanexx.common.ui.bottombar.Tabs.Notifications
import ru.mareanexx.common.ui.bottombar.Tabs.Settings


@Composable
fun MainTabScreen(rootNavController: NavHostController) {
    val navItems: List<ru.mareanexx.common.ui.bottombar.Tabs> = listOf(Home, Collections, Notifications, Settings)
    var selectedTab by rememberSaveable { mutableStateOf(Home) }
    var showBottomNavBar by remember { mutableStateOf(true) }

    val homeNavController = rememberNavController()
    val collectionsNavController = rememberNavController()
    val notificationsNavController = rememberNavController()
    val settingsNavController = rememberNavController()

    val navControllers = mapOf(
        Home to homeNavController,
        Collections to collectionsNavController,
        Notifications to notificationsNavController,
        Settings to settingsNavController,
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomNavBar) {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            navItems.forEach { tab ->
                val navController = navControllers[tab]!!
                val isSelected = tab == selectedTab

                if (isSelected) {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = tab.route
                    ) {
                        when (tab) {
                            Home -> {
                                composable(
                                    route = "${tab.route}", // TODO()
                                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                                ) {
                                    showBottomNavBar = true
                                }
                            }
                            Collections -> {
                                composable(route = Collections.route) {}
                            }
                            Notifications -> {
                                composable(route = Notifications.route) {}
                            }
                            Settings -> {
                                composable(route = Settings.route) {
                                    SettingsScreen(onNavigateToSettings = { navController.navigate(it.route) })
                                }

                                composable(
                                    route = SettingsRoute.Profile.route,
                                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                                ) {
                                    // TODO()
                                }
                                
                                composable(
                                    route = SettingsRoute.Others.route,
                                    enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
                                    exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
                                ) {
                                    OtherSettingsScreen(onNavigateBack = { navController.popBackStack() })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}