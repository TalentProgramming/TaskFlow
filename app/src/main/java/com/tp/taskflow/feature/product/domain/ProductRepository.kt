package com.tp.taskflow.feature.product.domain

import com.tp.taskflow.core.common.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun search(query: String): Flow<Resource<List<Product>>>
}
