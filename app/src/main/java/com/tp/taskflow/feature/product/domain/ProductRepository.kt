package com.tp.taskflow.feature.product.domain

import androidx.paging.PagingData
import com.tp.taskflow.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun search(query: String): Flow<Resource<List<Product>>>
    fun paged(query: String): Flow<PagingData<Product>>
    suspend fun refresh(query: String)
}
