package com.priyank.circulate.models

import com.priyank.circulate.authentication.model.UserInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class UserInfoTest {

    @Test
    fun createUserInfoWithValues() {
        val name = "John Doe"
        val email = "johndoe@example.com"
        val profilePhotoUrl = "https://example.com/profile.jpg"

        val userInfo = UserInfo(name, email, profilePhotoUrl)

        assertEquals(name, userInfo.name)
        assertEquals(email, userInfo.email)
        assertEquals(profilePhotoUrl, userInfo.profilePhotoUrl)
    }

    @Test
    fun createUserInfoWithNullValues() {
        val userInfo = UserInfo()

        assertEquals(null, userInfo.name)
        assertEquals(null, userInfo.email)
        assertEquals(null, userInfo.profilePhotoUrl)
    }
}
