package ru.mareanexx.common.ui.navigation

sealed class NavRoute(val route: String) {
    data object Start : NavRoute(route = "start")
    data object Login : NavRoute(route = "login")
    data object Registration : NavRoute(route = "register")
    data object Main : NavRoute(route = "main")
}