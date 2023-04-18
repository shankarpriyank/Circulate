package com.priyank.circulate.navigation

sealed class Screen(val route: String) {
    object Authentication : Screen(route = "authentication")
    object Main : Screen(route = "main")
}
