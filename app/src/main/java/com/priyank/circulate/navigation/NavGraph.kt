package com.priyank.circulate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priyank.circulate.authentication.presentation.AuthenticationScreen
import com.priyank.circulate.main.Greeting

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Authentication.route) {
            AuthenticationScreen(navHostController = navController)
        }
        composable(route = Screen.Main.route) {
            Greeting(navHostController = navController)
        }
    }
}
