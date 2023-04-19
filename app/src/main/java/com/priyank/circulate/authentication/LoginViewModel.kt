package com.priyank.circulate.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.priyank.circulate.R
import com.priyank.circulate.authentication.data.UserDetails
import com.priyank.circulate.authentication.model.Info
import com.priyank.circulate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val userDetails: UserDetails,
    private val gsc: GoogleSignInClient,
) : ViewModel() {

    fun data(): List<Info> {

        return listOf(
            Info(
                "Circulate Value in your community", R.drawable.ic2
            ),
            Info(
                "Leverage the power of your local community", R.drawable.ic_1
            ),
            Info(
                "Collaborate and Create \n Together and better",
                R.drawable.ic_3
            )

        )
    }

    init {
    }

    fun fetchSignInUser(
        id: String?,
        email: String?,
        name: String?,
        profilePhotoUrl: String? = null,
        navHostController: NavHostController
    ) {
        Log.i("User ", "SignedIn")
        userDetails.updateUser(
            id = id,
            name = name,
            email = email,
            profilePhotoUrl = profilePhotoUrl,
        )
        Log.i("Name", name.toString())
        Log.i("Email", email.toString())
        Log.i("Url", profilePhotoUrl.toString())
        Log.i("Id", id.toString())
        navHostController.popBackStack()
        navHostController.navigate(Screen.Main.route)
    }

    fun signOutUser() {
        Log.i("Signout", "Signout Successful")
        userDetails.signOut()
        gsc.signOut()
    }
}
