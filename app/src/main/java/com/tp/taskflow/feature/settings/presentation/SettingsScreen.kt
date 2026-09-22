package com.tp.taskflow.feature.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.ui.theme.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    darkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = spacing.md)
        ) {
            Text("Chapter 18 • Material 3 lists and slots", color = MaterialTheme.colorScheme.primary)
            EnvironmentBanner()
            SettingToggleRow(
                title = "Dark theme",
                subtitle = "Use OLED black",
                checked = darkMode,
                onCheckedChange = onToggleDarkMode,
                icon = Icons.Default.DarkMode
            )
            SettingToggleRow(
                title = "Notifications",
                subtitle = "Classroom reminder (session only)",
                checked = notifications,
                onCheckedChange = viewModel::setNotifications,
                icon = Icons.Default.Notifications
            )
            OutlinedButton(onClick = onBack, modifier = Modifier.padding(top = spacing.md)) {
                Text("Back")
            }
        }
    }
}
