package com.priyank.circulate.main.model

import com.priyank.circulate.authentication.model.UserInfo

data class Post(
    val createdBy: UserInfo,
    val description: String,
    val imageUrl: String?,
    val timeOfPost: Long = System.currentTimeMillis(),
    val comments: List<Comment>? = null,
    val upVotes: Long = 0
)
