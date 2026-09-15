package com.tp.taskflow.feature.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.auth.domain.LoginUseCase
import com.tp.taskflow.feature.auth.domain.User
import com.tp.taskflow.utils.isStrongPassword
import com.tp.taskflow.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var state by mutableStateOf<Resource<User>>(Resource.Empty)
        private set

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
        val trimmed = email.trim()
        if (!trimmed.isValidEmail() || !password.isStrongPassword()) {
            state = Resource.Error("Please enter a valid email and a password with at least 6 characters")
            return
        }
        viewModelScope.launch {
            state = Resource.Loading
            state = login(trimmed, password)
        }
    }
}
