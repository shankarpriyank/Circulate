package com.priyank.circulate.authentication.data

import android.content.SharedPreferences
import com.priyank.circulate.authentication.model.UserInfo

class UserDetails(
    private val sharedPreferences: SharedPreferences,
) {
    fun isLoggedIn() = sharedPreferences.getBoolean("Logged In", false)
    fun getUserId() = sharedPreferences.getString("userId", "F")
    fun getUserEmail() = sharedPreferences.getString("userEmail", "F")
    fun getUserName() = sharedPreferences.getString("userName", "F")
    fun getUserObject() = UserInfo(sharedPreferences.getString("userName", "F"), sharedPreferences.getString("userEmail", "F"), sharedPreferences.getString("userImageUrl", "F"))

    fun updateUser(
        id: String?,
        email: String?,
        name: String?,
        profilePhotoUrl: String? = null,
    ) = with(sharedPreferences.edit()) {
        putString("userId", id)
        putString("userName", name)
        putString("userEmail", email)
        putString("userImageUrl", profilePhotoUrl)
        putBoolean("Logged In", true)
        apply()
    }

    fun signOut() = with(sharedPreferences.edit()) {
        putBoolean("Logged In", false)
        apply()
    }
}
