package com.priyank.circulate

import com.priyank.circulate.authentication.data.UserDetails
import com.priyank.circulate.navigation.Screen
import com.priyank.circulate.utils.TestDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashScreenViewModelTest {
    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private val userStorage: UserDetails = mockk(relaxed = true)

    @Test
    fun `on init with user logged in, should update start destination state to screen detail`() =
        runTest {
            coEvery { userStorage.isLoggedIn() } returns true
            val viewModel = SplashScreenViewModel(userStorage)
            assertEquals(Screen.Main.route, viewModel.startDestination.value)
            assertFalse(viewModel.showSplashScreen.value)
        }

    @Test
    fun `on init with user logged in, should update start destination state to screen authentication`() =
        runTest {
            coEvery { userStorage.isLoggedIn() } returns false
            val viewModel = SplashScreenViewModel(userStorage)
            assertEquals(Screen.Authentication.route, viewModel.startDestination.value)
            assertFalse(viewModel.showSplashScreen.value)
        }
}
