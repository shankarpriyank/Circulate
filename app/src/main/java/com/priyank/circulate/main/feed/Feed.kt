package com.priyank.circulate.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyank.circulate.main.MainViewModel
import com.priyank.circulate.main.feed.composables.PostItem
import com.priyank.circulate.ui.theme.LightGrey

@Composable
fun Feed(vm: MainViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = LightGrey)
    ) {
        val posts = vm.feed.collectAsState().value

        LaunchedEffect(key1 = true) {
            vm.getAllPosts()
        }

        for (post in posts) {
            PostItem(
                profileImageUrl = post.createdBy!!.profilePhotoUrl!!,
                createdBy = post.createdBy.name!!,
                description = post.description!!,
                imageUrl = post.imageUrl,
                timeOfPost = post.timeOfPost!!,
                comments = post.comments
            )
        }
    }
}
