package com.ashu.savemytax.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashu.savemytax.repository.user.UserRepository
import com.ashu.savemytax.data.NotificationToken
import com.ashu.savemytax.data.RegisterResponse
import com.ashu.savemytax.data.RegisterUser
import com.ashu.savemytax.utils.Resource
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    private val _register = MutableLiveData<Resource<RegisterResponse>>()
    val register: LiveData<Resource<RegisterResponse>>
        get() = _register
    private val _push = MutableLiveData<Resource<Boolean>>()
    val push: LiveData<Resource<Boolean>>
        get() = _push

    private var showOneTapUI = true

    fun getSigninRequest(clientKey: String): BeginSignInRequest {

        return BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(clientKey)
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()
    }

    fun updateToken(notificationToken: NotificationToken) = viewModelScope.launch {
        _push.postValue(Resource.loading(null))
        try {
            userRepository.updateNotificationToken(notificationToken).let {
                if (it.isSuccessful) {
                    _push.postValue(Resource.success(true))
                } else {
                    _push.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        } catch (e: Exception) {
            Log.d("updation of token", "tokenizer kahin ka failed")
        }

    }

    fun loginVerification(oneTapClient: SignInClient, data: Intent?) {
        val credential = oneTapClient.getSignInCredentialFromIntent(data)
        val idToken = credential.googleIdToken
        val username = credential.id
        val password = credential.password
        when {
            idToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with your backend.
                registerUser(RegisterUser(idToken))
                Log.d("Login3", "Got ID token." + idToken)
            }
            password != null -> {
                // Got a saved username and password. Use them to authenticate
                // with your backend.
                Log.d("Login4", "Got password.")
            }
            else -> {
                // S
                // houldn't happen.
                Log.d("Login5", "No ID token or password!")
            }
        }
    }

    fun handleLoginException(e: ApiException) {
        when (e.statusCode) {
            CommonStatusCodes.CANCELED -> {
                Log.d("Login6", "One-tap dialog was closed.")
                // Don't re-prompt the user.
                showOneTapUI = false
            }
            CommonStatusCodes.NETWORK_ERROR -> {
                Log.d("Login7", "One-tap encountered a network error.")
                // Try again or just ignore.
            }
            else -> {
                Log.d("Login8", "Couldn't get credential from result." +
                        " (${e.localizedMessage})")
            }
        }
    }

    private fun registerUser(registerUser: RegisterUser) = viewModelScope.launch {
        _register.postValue(Resource.loading(null))
        try {
            userRepository.registerUser(registerUser). let {
                if (it.isSuccessful) {
                    if (it.code() == 208) {
                        _register.postValue(Resource.alreadyRegistered("already_registered", it.body()))
                    } else {
                        _register.postValue(Resource.success(it.body()))
                    }
                } else {
                    _register.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        } catch (e: Exception) {
            Log.d("Login Failed", "registration failed")
        }
    }



}