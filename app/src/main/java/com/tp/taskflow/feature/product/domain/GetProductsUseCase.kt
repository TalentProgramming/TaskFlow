package com.tp.taskflow.feature.product.domain

import com.tp.taskflow.core.common.Resource
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Product>>> = repository.search("")
}
