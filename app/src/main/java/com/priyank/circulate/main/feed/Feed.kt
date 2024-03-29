package com.priyank.circulate.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.priyank.circulate.main.MainViewModel
import com.priyank.circulate.main.feed.composables.PostItem
import com.priyank.circulate.ui.theme.LightGrey

@Composable
fun Feed(vm: MainViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier.padding(bottom = 52.dp)
            .verticalScroll(rememberScrollState())
            .background(color = LightGrey)
    ) {
        val posts = vm.feed.collectAsState().value

        LaunchedEffect(key1 = true) {
            vm.getAllPosts()
        }

        for (post in posts) {
            PostItem(
                contactEmail = post.createdBy!!.email!!,
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
