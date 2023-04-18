package com.priyank.circulate.main.model

import com.priyank.circulate.authentication.model.UserInfo

data class Comment(
    val commentBy: UserInfo,
    val comment: String
)
