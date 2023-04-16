package com.priyank.circulate.authentication.presentation

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.priyank.circulate.R
import com.priyank.circulate.authentication.GoogleApiContract

@Composable
fun AuthenticationScreen() {
    val context = LocalContext.current
    val signInRequestCode = 1
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)

                if (gsa != null) {
                    Toast.makeText(context, "Login Successfull", Toast.LENGTH_LONG).show()
                } else {
                    Log.e("Login Failed", "Error")
                }
            } catch (e: ApiException) {
                Log.e("Error in AuthScreen%s", e.toString())
            }
        }

    SignInButton(
        modifier = Modifier.padding(vertical = 16.dp),
        text = "Sign in with Google",
        loadingText = "Signing in...",
        isLoading = false,
        icon = painterResource(id = R.drawable.ic_google_login),
        onClick = { authResultLauncher.launch(signInRequestCode) },
    )
}
