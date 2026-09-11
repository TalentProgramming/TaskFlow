package com.tp.taskflow.feature.auth.presentation

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.auth.data.FakeAuthRepository
import com.tp.taskflow.feature.auth.domain.User
import com.tp.taskflow.utils.isStrongPassword
import com.tp.taskflow.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FakeAuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var state by mutableStateOf<Resource<User>>(Resource.Empty)
        private set

    private val mainHandler = Handler(Looper.getMainLooper())

    fun onEmailChange(value: String) {
        email = value
        if (state !is Resource.Loading) {
            state = Resource.Empty
        }
    }

    fun onPasswordChange(value: String) {
        password = value
        if (state !is Resource.Loading) {
            state = Resource.Empty
        }
    }

    fun onLoginClick() {
        val credentials = email.trim().let { trimmedEmail ->
            trimmedEmail to password
        }

        if (!credentials.first.isValidEmail() || !credentials.second.isStrongPassword()) {
            state = Resource.Error("Please enter a valid email and a password with at least 6 characters")
            return
        }

        state = Resource.Loading
        mainHandler.removeCallbacksAndMessages(null)
        mainHandler.postDelayed({
            state = repository.login(credentials.first, credentials.second)
        }, LOADING_DELAY_MS)
    }

    override fun onCleared() {
        mainHandler.removeCallbacksAndMessages(null)
        super.onCleared()
    }

    companion object {
        private const val LOADING_DELAY_MS = 800L
    }
}
