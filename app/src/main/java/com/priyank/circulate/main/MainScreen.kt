package com.priyank.circulate.main

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PostAdd
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.priyank.circulate.main.upload.UploadViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Greeting(
    navHostController: NavHostController,
    vm: UploadViewModel = hiltViewModel()
) {
    val navControllerForBottomNav = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "home",
                        icon = Icons.Outlined.Home
                    ),
                    BottomNavItem(
                        name = "Upload",
                        route = "upload",
                        icon = Icons.Outlined.PostAdd
                    ),

                    BottomNavItem(
                        name = "Profile",
                        route = "profile",
                        icon = Icons.Outlined.Person
                    )
                ),
                navController = navControllerForBottomNav,
                onItemClick = {
                    navControllerForBottomNav.navigate(it.route)
                }
            )
        }
    ) {
        Navigation(
            navController = navControllerForBottomNav,
            navControllerforSigningOut = navHostController
        )
    }
}
