package com.tp.taskflow.di

import com.tp.taskflow.feature.product.data.ProductRepositoryImpl
import com.tp.taskflow.feature.product.domain.GetProductsUseCase
import com.tp.taskflow.feature.product.domain.SearchProductsUseCase

/**
 * Manual graph until Chapter 6 (Hilt).
 * Presentation may depend on use cases from here — not on ProductRepositoryImpl.
 */
object ProductGraph {
    private val repository = ProductRepositoryImpl()
    val getProducts = GetProductsUseCase(repository)
    val searchProducts = SearchProductsUseCase(repository)
}
