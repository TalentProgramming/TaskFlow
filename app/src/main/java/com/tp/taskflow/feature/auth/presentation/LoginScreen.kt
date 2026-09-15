package com.tp.taskflow.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.feature.auth.data.FakeAuthRepository
import com.tp.taskflow.feature.auth.domain.User

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onContinueToDashboard: () -> Unit = {},
    onOpenRegister: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "TaskFlow",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Chapter 2 • Login state simulator",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            EnvironmentBanner()
            Text(
                text = "Demo: ${FakeAuthRepository.DEMO_EMAIL} / ${FakeAuthRepository.DEMO_PASSWORD}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = viewModel.state !is Resource.Loading
            )
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                enabled = viewModel.state !is Resource.Loading
            )
            Button(
                onClick = viewModel::onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.state !is Resource.Loading
            ) {
                Text("Login")
            }
            OutlinedButton(onClick = onOpenRegister, modifier = Modifier.fillMaxWidth()) {
                Text("Create account")
            }
            LoginStatePanel(
                state = viewModel.state,
                onContinueToDashboard = onContinueToDashboard
            )
        }
    }
}

@Composable
private fun LoginStatePanel(
    state: Resource<User>,
    onContinueToDashboard: () -> Unit
) {
    when (state) {
        Resource.Empty -> {
            StatusCard(
                title = "Idle",
                message = "Enter credentials to sign in."
            )
        }
        Resource.Loading -> {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()
                    Text("Signing in...")
                }
            }
        }
        is Resource.Success -> {
            val welcome = state.data.run { "Welcome, $name" }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                StatusCard(
                    title = "Success",
                    message = "$welcome\n${state.data.email}",
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
                Button(
                    onClick = onContinueToDashboard,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open dashboard")
                }
            }
        }
        is Resource.Error -> {
            StatusCard(
                title = "Error",
                message = state.message,
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        }
    }
}

@Composable
private fun StatusCard(
    title: String,
    message: String,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
