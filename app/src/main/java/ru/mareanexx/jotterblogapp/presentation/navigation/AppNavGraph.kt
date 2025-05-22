package ru.mareanexx.jotterblogapp.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mareanexx.common.ui.navigation.NavRoute
import ru.mareanexx.feature_auth.presentation.screen.login.LoginScreen
import ru.mareanexx.feature_auth.presentation.screen.register.RegisterScreen
import ru.mareanexx.feature_auth.presentation.screen.start.StartScreen

@Composable
fun AppNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.Main.route
    ) {
        composable(
            route = NavRoute.Start.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
        ) {
            StartScreen(
                onNavigateToLoginScreen = { navHostController.navigate(NavRoute.Login.route) },
                onNavigateToRegisterScreen = { navHostController.navigate(NavRoute.Registration.route) }
            )
        }
        composable(route = NavRoute.Login.route) {
            LoginScreen(
                onNavigateToRegisterScreen = { navHostController.navigate(NavRoute.Registration.route) },
                onNavigateBack = { navHostController.popBackStack() },
                onNavigateToProfile = {
                    navHostController.navigate("main") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(route = NavRoute.Registration.route) {
            RegisterScreen(
                onNavigateToLoginScreen = { navHostController.navigate(NavRoute.Login.route) },
                onNavigateBack = { navHostController.popBackStack() },
                onNavigateToProfile = {
                    navHostController.navigate("main") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(route = NavRoute.Main.route) {
            MainTabScreen(navHostController)
        }
    }
}