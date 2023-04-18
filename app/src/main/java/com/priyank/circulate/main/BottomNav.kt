package com.priyank.circulate.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.priyank.circulate.authentication.model.UserInfo
import com.priyank.circulate.main.model.Post
import com.priyank.circulate.ui.theme.PrimaryOrange

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = White,
        elevation = 5.dp,
        contentColor = Blue
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = PrimaryOrange,
                unselectedContentColor = Black,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Text(text = item.badgeCount.toString())
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, navControllerforSigningOut: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Test("home")
        }
        composable("upload") {
            Test("upload")
        }
        composable("profile") {
            Test("profile")
        }
    }
}

@Composable
fun Test(ss: String) {
    Button(onClick = { testDb() }) {
        Text(text = ss)
    }
}

fun testDb() {
    // Write a message to the database
    val database = Firebase.firestore
    val myRef = database.collection("post")

    for (i in 0 until 100) {
        val user = Post(
            createdBy = UserInfo("t000t$i", "bb", "gg"),
            description = "Hola$i",
            ImageUrl = "ss",
            comments = null

        )

// Add a new document with a generated ID
        myRef
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}
