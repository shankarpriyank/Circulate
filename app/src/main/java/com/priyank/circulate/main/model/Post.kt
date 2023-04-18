package com.priyank.circulate.main.model

import com.priyank.circulate.authentication.model.UserInfo

data class Post(
    val createdBy: UserInfo,
    val description: String,
    val ImageUrl: String?,
    val comments: List<Comment>?
)
