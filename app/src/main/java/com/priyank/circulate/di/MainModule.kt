package com.priyank.circulate.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.priyank.circulate.R
import com.priyank.circulate.authentication.data.UserDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideGsa(@ApplicationContext context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    @Singleton
    @Provides
    fun provideUserDetails(@ApplicationContext context: Context): UserDetails {
        val sharedPreferences = context.getSharedPreferences("accountDetails", MODE_PRIVATE)
        return UserDetails(sharedPreferences)
    }

    @Provides
    fun provideGoogleSigninClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.credentials))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        return GoogleSignIn.getClient(context, gso)
    }
}
