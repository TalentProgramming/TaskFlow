package com.tp.taskflow.feature.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.feature.profile.domain.Profile
import com.tp.taskflow.ui.theme.TaskFlowTheme

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileContent(state = state, onBack = onBack, onRetry = viewModel::refresh)
}

@Composable
fun ProfileContent(
    state: Resource<Profile>,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Profile", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Chapter 10 • Compose profile", color = MaterialTheme.colorScheme.primary)
            EnvironmentBanner()
            when (state) {
                Resource.Loading, Resource.Empty -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                is Resource.Error -> {
                    Text(state.message)
                    Button(onClick = onRetry) { Text("Retry") }
                }
                is Resource.Success -> {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(state.data.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(state.data.email)
                            Text("Photo: classroom mock", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    TaskFlowTheme {
        ProfileContent(
            state = Resource.Success(Profile("u-1", "Aung Ko", "student@example.com")),
            onBack = {},
            onRetry = {}
        )
    }
}
