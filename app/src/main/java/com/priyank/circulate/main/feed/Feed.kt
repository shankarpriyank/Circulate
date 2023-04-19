package com.priyank.circulate.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.priyank.circulate.main.feed.composables.PostItem
import com.priyank.circulate.ui.theme.LightGrey

@Composable
fun Feed() {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()).background(color = LightGrey)
    ) {
        for (i in 0..100) {
            PostItem(createdBy = "Name  ", description = "Lorem Ipsum ", imageUrl = "https://i.stack.imgur.com/Trj9n.jpg", timeOfPost = 1223, comments = null)
            PostItem(createdBy = "Name  ", description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum", imageUrl = null, timeOfPost = 1223, comments = null)
        }
    }
}
