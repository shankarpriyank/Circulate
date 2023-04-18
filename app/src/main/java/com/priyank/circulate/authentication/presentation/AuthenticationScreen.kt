package com.priyank.circulate.authentication.presentation

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.common.api.ApiException
import com.priyank.circulate.R
import com.priyank.circulate.authentication.GoogleApiContract
import com.priyank.circulate.authentication.LoginViewModel
import com.priyank.circulate.authentication.presentation.composables.Slider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun AuthenticationScreen(navHostController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val signInRequestCode = 1
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)

                if (gsa != null) {
                    viewModel.fetchSignInUser(
                        id = gsa.id,
                        email = gsa.email,
                        name = gsa.displayName,
                        profilePhotoUrl = gsa.photoUrl.toString(),
                        navHostController = navHostController
                    )
                } else {
                    Log.e("Login Failed", "Error")
                }
            } catch (e: ApiException) {
                Log.e("Error in AuthScreen%s", e.toString())
            }
        }

    val pagerState = rememberPagerState(pageCount = 3)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight(0.8f)
        ) { page ->
            Slider(info = viewModel.data()[page])
        }

        HorizontalPagerIndicator(
            modifier = Modifier.padding(bottom = 60.dp),
            pagerState = pagerState,
            indicatorShape = CircleShape,
            activeColor = Color.Black, inactiveColor = Color.Gray
        )

        LaunchedEffect(key1 = pagerState.currentPage) {
            launch {
                delay(3000)
                with(pagerState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                    animateScrollToPage(
                        page = target,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        }

        SignInButton(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp).fillMaxWidth(),
            text = "Sign in with Google",
            loadingText = "Signing in...",
            isLoading = false,
            icon = painterResource(id = R.drawable.ic_google_login),
            onClick = { authResultLauncher.launch(signInRequestCode) },
        )
    }
}
