package com.priyank.circulate

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.priyank.circulate.authentication.LoginViewModel
import com.priyank.circulate.authentication.data.UserDetails
import com.priyank.circulate.authentication.model.Info
import com.priyank.circulate.utils.TestDispatcherRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val userDetails: UserDetails = mockk(relaxed = true)
    private val googleSignInClient: GoogleSignInClient = mockk(relaxed = true)
    private val viewModel = LoginViewModel(
        userDetails,
        gsc = googleSignInClient,
    )

    @Test
    fun `on data(), should return info list`() {
        val expected = listOf(
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
        assertEquals(expected, viewModel.data())
    }

//    @Test
//    fun `on fetchSignInUser(), should update user and navigate back to screen detail`() {
//        val navHostController: NavHostController = mockk(relaxed = true)
//        viewModel.fetchSignInUser(
//            id = "id",
//            name = "name",
//            email = "email",
//            profilePhotoUrl = "profile photo url",
//            navHostController = navHostController,
//        )
//
//        verify {
//            userDetails.updateUser(
//                id = "id",
//                name = "name",
//                email = "email",
//                profilePhotoUrl = "profile photo url",
//            )
//            navHostController.popBackStack()
//            navHostController.navigate(Screen.Main.route)
//        }
//    }
//
//    @Test
//    fun `on signOutUser(), should call user sign out and sign out from Google`() {
//        viewModel.signOutUser()
//
//        verify {
//            userDetails.signOut()
//         //   googleSignInClient.signOut()
//        }
//    }
}
