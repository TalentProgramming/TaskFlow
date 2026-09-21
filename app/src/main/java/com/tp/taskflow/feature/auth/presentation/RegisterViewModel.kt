package com.tp.taskflow.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.auth.domain.RegisterForm
import com.tp.taskflow.feature.auth.domain.RegisterUseCase
import com.tp.taskflow.feature.auth.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: RegisterUseCase
) : ViewModel() {

    private val _form = MutableStateFlow(RegisterForm())
    val form = _form.asStateFlow()

    private val _state = MutableStateFlow<Resource<User>>(Resource.Empty)
    val state = _state.asStateFlow()

    fun update(transform: RegisterForm.() -> RegisterForm) {
        _form.value = _form.value.transform().copy(errors = emptyMap())
        if (_state.value !is Resource.Loading) {
            _state.value = Resource.Empty
        }
    }

    fun submit() {
        val checked = _form.value.validated()
        _form.value = checked
        if (checked.errors.isNotEmpty()) return
        viewModelScope.launch {
            _state.value = Resource.Loading
            _state.value = register(
                name = checked.name.trim(),
                email = checked.email.trim(),
                password = checked.password,
                phone = checked.phone.trim()
            )
        }
    }
}
