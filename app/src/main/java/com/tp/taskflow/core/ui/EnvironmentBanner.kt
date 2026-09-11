package com.tp.taskflow.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tp.taskflow.core.network.ApiConfig

@Composable
fun EnvironmentBanner(modifier: Modifier = Modifier) {
    Text(
        text = "${ApiConfig.flavor} • ${ApiConfig.buildType} • ${ApiConfig.baseUrl}",
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
