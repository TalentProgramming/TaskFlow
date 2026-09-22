package com.tp.taskflow.feature.product.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.ui.theme.TaskFlowTheme

@Composable
fun ProductDetailScreen(viewModel: ProductDetailViewModel, onBack: () -> Unit) {
    val favorite by viewModel.favorite.collectAsStateWithLifecycle()
    val banner by viewModel.banner.collectAsStateWithLifecycle()
    ProductDetailContent(
        product = viewModel.product,
        favorite = favorite,
        showBanner = banner,
        onToggleFavorite = viewModel::toggleFavorite,
        onBack = onBack
    )
}

@Composable
fun ProductDetailContent(
    product: StudioProduct,
    favorite: Boolean = false,
    showBanner: Boolean = false,
    onToggleFavorite: () -> Unit = {},
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val ringColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.surfaceVariant
    val spoken = "${product.name}, ${product.price}, ${if (favorite) "favorited" else "not favorited"}"
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .semantics(mergeDescendants = true) { contentDescription = spoken }
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(96.dp)
                        .drawBehind {
                            drawArc(
                                color = trackColor,
                                startAngle = -90f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                            )
                            drawArc(
                                color = ringColor,
                                startAngle = -90f,
                                sweepAngle = 360f * product.progress,
                                useCenter = false,
                                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }
                )
                Text(
                    text = "TaskFlow",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                SaleBadge(modifier = Modifier.align(Alignment.TopEnd))
                FavoriteIcon(
                    favorite = favorite,
                    onClick = onToggleFavorite,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Chapter 19 • Motion, TalkBack, drawing", color = MaterialTheme.colorScheme.primary)
                EnvironmentBanner()
                AnimatedVisibility(visible = showBanner) {
                    Text("Saved to favorites", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(product.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        "${product.price}  •  ${(product.progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(product.description, style = MaterialTheme.typography.bodyMedium)
                OutlinedButton(onClick = onBack) { Text("Back") }
            }
            Button(
                onClick = { Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Add to cart")
            }
        }
    }
}

@Composable
fun SaleBadge(modifier: Modifier = Modifier) {
    // Modifier quiz — pad then paint (this is the live badge):
    // Modifier.padding(12.dp).background(error).clip(RoundedCornerShape(8.dp))
    // Reverse (do not use live): Modifier.clip(...).background(...).padding(12.dp)
    Text(
        text = "SALE",
        color = MaterialTheme.colorScheme.onError,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.error)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailPreview() {
    TaskFlowTheme {
        ProductDetailContent(
            product = ProductDetailViewModel.studioProduct("studio-mug"),
            favorite = true,
            showBanner = true,
            onBack = {}
        )
    }
}
