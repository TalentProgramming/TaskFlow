package com.tp.taskflow.feature.product.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.ui.theme.TaskFlowTheme

@Composable
fun ProductDetailScreen(viewModel: ProductDetailViewModel, onBack: () -> Unit) {
    ProductDetailContent(product = viewModel.product, onBack = onBack)
}

@Composable
fun ProductDetailContent(product: StudioProduct, onBack: () -> Unit) {
    val context = LocalContext.current
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
            ) {
                Text(
                    text = "TaskFlow",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                SaleBadge(modifier = Modifier.align(Alignment.TopEnd))
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Chapter 17 • Layouts and modifiers", color = MaterialTheme.colorScheme.primary)
                EnvironmentBanner()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(product.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(product.price, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }
                Text(product.description, style = MaterialTheme.typography.bodyMedium)
                Text("id = ${product.id}", style = MaterialTheme.typography.bodySmall)
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
private fun SaleBadge(modifier: Modifier = Modifier) {
    // Modifier quiz — pad then paint (this is the live badge):
    // Modifier.padding(12.dp).background(error).clip(RoundedCornerShape(8.dp))
    // Reverse (do not use live): Modifier.clip(...).background(...).padding(12.dp)
    // The reverse paints a full-bleed color then insets the text, so the badge looks larger.
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
            onBack = {}
        )
    }
}
