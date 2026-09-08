package com.tp.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tp.taskflow.feature.auth.presentation.LoginScreen
import com.tp.taskflow.feature.auth.presentation.LoginViewModel
import com.tp.taskflow.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskFlowTheme {
                val loginViewModel: LoginViewModel = viewModel()
                LoginScreen(viewModel = loginViewModel)
            }
        }
    }
}
