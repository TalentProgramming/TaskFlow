package com.tp.taskflow.feature.product.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FavoriteIcon(
    favorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (favorite) 1.2f else 1f,
        label = "favoriteScale"
    )
    val description = if (favorite) "Remove favorite" else "Add favorite"
    Text(
        text = "♥",
        fontSize = 28.sp,
        color = if (favorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
            .scale(scale)
            .semantics { contentDescription = description }
            .clickable(onClick = onClick)
            .padding(8.dp)
    )
}
