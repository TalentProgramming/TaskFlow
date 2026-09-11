package com.tp.taskflow.feature.product.domain

import com.tp.taskflow.core.common.Resource
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Product>>> = repository.search("")
}
