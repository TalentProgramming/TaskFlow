package com.tp.taskflow.feature.product.domain

import com.tp.taskflow.core.common.Resource
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Product>>> = repository.search(query)
}
