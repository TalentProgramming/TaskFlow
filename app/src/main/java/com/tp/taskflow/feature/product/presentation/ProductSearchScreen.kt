package com.tp.taskflow.feature.product.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.feature.product.domain.Product

@Composable
fun ProductSearchScreen(
    viewModel: ProductSearchViewModel,
    onBack: () -> Unit
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val paged = viewModel.pagedProducts.collectAsLazyPagingItems()
    val refresh = paged.loadState.refresh
    val append = paged.loadState.append
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("TaskFlow", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Chapter 9 • Offline + Paging",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    EnvironmentBanner()
                }
                OutlinedButton(onClick = onBack) {
                    Text("Back")
                }
            }
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search products") },
                supportingText = {
                    Text("40 products • 8 per page. Scroll for pages 2–5. Type error for HTTP 500.")
                },
                singleLine = true
            )
            when {
                refresh is LoadState.Loading && paged.itemCount == 0 -> {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text("Loading page 1…", modifier = Modifier.padding(top = 12.dp))
                    }
                }
                refresh is LoadState.Error && paged.itemCount == 0 -> {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Could not search", fontWeight = FontWeight.SemiBold)
                            Text(refresh.error.message ?: "Search failed")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(paged.itemCount) { index ->
                            val product = paged[index] ?: return@items
                            ProductCard(product = product, onClick = { viewModel.onProductClick(product) })
                        }
                        if (append is LoadState.Loading) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                    Text("Loading next page…", modifier = Modifier.padding(top = 8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(product.name, fontWeight = FontWeight.SemiBold)
            Text("${product.category} • $${"%.2f".format(product.price)}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
