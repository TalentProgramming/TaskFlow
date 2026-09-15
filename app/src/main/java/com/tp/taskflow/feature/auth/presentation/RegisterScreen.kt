package com.tp.taskflow.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.ui.TaskTextField

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val form by viewModel.form.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Create account", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Chapter 11 • Form state in the ViewModel", color = MaterialTheme.colorScheme.primary)
            TaskTextField(form.name, { viewModel.update { copy(name = it) } }, "Name", form.errors["name"])
            TaskTextField(
                form.email,
                { viewModel.update { copy(email = it) } },
                "Email",
                form.errors["email"],
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            TaskTextField(
                form.password,
                { viewModel.update { copy(password = it) } },
                "Password",
                form.errors["password"],
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            TaskTextField(
                form.confirm,
                { viewModel.update { copy(confirm = it) } },
                "Confirm password",
                form.errors["confirm"],
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            TaskTextField(
                form.phone,
                { viewModel.update { copy(phone = it) } },
                "Phone",
                form.errors["phone"],
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Button(
                onClick = viewModel::submit,
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is Resource.Loading
            ) { Text("Register") }
            when (state) {
                Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                is Resource.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
                is Resource.Success -> {
                    Text("Welcome, ${state.data.name}")
                    Button(onClick = onSuccess, modifier = Modifier.fillMaxWidth()) { Text("Open dashboard") }
                }
                Resource.Empty -> Unit
            }
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back to login") }
        }
    }
}
