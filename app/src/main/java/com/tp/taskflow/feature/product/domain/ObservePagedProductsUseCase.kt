package com.tp.taskflow.feature.product.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePagedProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Product>> = repository.paged(query)
}
