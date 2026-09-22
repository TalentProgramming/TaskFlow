package com.tp.taskflow.feature.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.auth.domain.AuthRepository
import com.tp.taskflow.feature.home.domain.Dashboard
import com.tp.taskflow.feature.home.domain.LoadDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val loadDashboard: LoadDashboardUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf<Resource<Dashboard>>(Resource.Empty)
        private set

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            state = Resource.Loading
            state = loadDashboard()
        }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onDone()
        }
    }
}
