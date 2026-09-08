package com.aal.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aal.taskflow.feature.auth.presentation.LoginScreen
import com.aal.taskflow.feature.auth.presentation.LoginViewModel
import com.aal.taskflow.feature.home.presentation.DashboardScreen
import com.aal.taskflow.feature.home.presentation.DashboardViewModel
import com.aal.taskflow.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                var destination by rememberSaveable { mutableStateOf(DESTINATION_LOGIN) }
                when (destination) {
                    DESTINATION_DASHBOARD -> {
                        val dashboardViewModel: DashboardViewModel = viewModel()
                        DashboardScreen(
                            viewModel = dashboardViewModel,
                            onLogout = { destination = DESTINATION_LOGIN }
                        )
                    }
                    else -> {
                        val loginViewModel: LoginViewModel = viewModel()
                        LoginScreen(
                            viewModel = loginViewModel,
                            onContinueToDashboard = { destination = DESTINATION_DASHBOARD }
                        )
                    }
                }
            }
        }
    }

    private companion object {
        const val DESTINATION_LOGIN = "login"
        const val DESTINATION_DASHBOARD = "dashboard"
    }
}
