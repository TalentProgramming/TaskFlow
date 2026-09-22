package com.tp.taskflow.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.feature.home.domain.AppNotification
import com.tp.taskflow.feature.home.domain.Dashboard
import com.tp.taskflow.feature.profile.domain.Profile
import com.tp.taskflow.feature.task.domain.TaskPost

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    darkMode: Boolean = false,
    onToggleDarkMode: (Boolean) -> Unit = {},
    onLogout: () -> Unit,
    onOpenSearch: () -> Unit = {},
    onOpenNotes: () -> Unit = {},
    onOpenProfile: () -> Unit = {},
    onOpenStudioMug: () -> Unit = {},
    onOpenSettings: () -> Unit = {}
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "TaskFlow",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Chapter 20 • Spacing and UI tests",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    EnvironmentBanner()
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Dark")
                        Switch(checked = darkMode, onCheckedChange = onToggleDarkMode)
                    }
                    OutlinedButton(onClick = onOpenSearch) { Text("Search") }
                    OutlinedButton(onClick = onOpenNotes) { Text("Notes") }
                    OutlinedButton(onClick = onOpenProfile) { Text("Profile") }
                    OutlinedButton(onClick = onOpenStudioMug) { Text("Studio Mug") }
                    OutlinedButton(onClick = onOpenSettings) { Text("Settings") }
                    OutlinedButton(onClick = onLogout) { Text("Logout") }
                }
            }

            when (val state = viewModel.state) {
                Resource.Empty -> Text("Dashboard has not loaded yet.")
                Resource.Loading -> LoadingPanel()
                is Resource.Error -> ErrorPanel(
                    message = state.message,
                    onRetry = viewModel::refresh
                )
                is Resource.Success -> DashboardContent(dashboard = state.data)
            }
        }
    }
}

@Composable
private fun LoadingPanel() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator()
            Text("Loading profile, posts, and notifications...")
        }
    }
}

@Composable
private fun ErrorPanel(message: String, onRetry: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Could not load dashboard", fontWeight = FontWeight.SemiBold)
            Text(message)
            Button(onClick = onRetry, modifier = Modifier.fillMaxWidth()) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun DashboardContent(dashboard: Dashboard) {
    ProfileCard(dashboard.profile)
    SectionTitle("Posts")
    dashboard.posts.forEach { post -> PostCard(post) }
    SectionTitle("Notifications")
    dashboard.notifications.forEach { notification -> NotificationCard(notification) }
}

@Composable
private fun ProfileCard(profile: Profile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Profile", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(profile.name, style = MaterialTheme.typography.titleMedium)
            Text(profile.email, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun PostCard(post: TaskPost) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(post.title, fontWeight = FontWeight.SemiBold)
            Text(post.note, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun NotificationCard(notification: AppNotification) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            val suffix = if (notification.unread) " • unread" else ""
            Text(notification.title + suffix, fontWeight = FontWeight.SemiBold)
            Text(notification.message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
}
