package com.aal.taskflow.feature.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aal.taskflow.core.common.Resource
import com.aal.taskflow.feature.home.data.DashboardRepositoryImpl
import com.aal.taskflow.feature.home.domain.Dashboard
import com.aal.taskflow.feature.home.domain.LoadDashboardUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val loadDashboard: LoadDashboardUseCase = LoadDashboardUseCase(
        DashboardRepositoryImpl()
    )
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
}
