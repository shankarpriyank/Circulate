package com.priyank.circulate

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyank.circulate.authentication.data.UserDetails
import com.priyank.circulate.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashScreenViewModel @Inject
constructor(
    private val userDetails: UserDetails,
) : ViewModel() {

    private val _showSplashScreen = MutableStateFlow(true)
    val showSplashScreen = _showSplashScreen.asStateFlow()
    private val _startDestination: MutableState<String> =
        mutableStateOf(Screen.Authentication.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            val isUserLoggedIn = userDetails.isLoggedIn()
            if (isUserLoggedIn) {
                Log.i("Is user signed in", isUserLoggedIn.toString())
                _startDestination.value = Screen.Main.route
            } else {
                Log.i("Is user signed in", isUserLoggedIn.toString())
                _startDestination.value = Screen.Authentication.route
            }
            _showSplashScreen.emit(false)
        }
    }
}
