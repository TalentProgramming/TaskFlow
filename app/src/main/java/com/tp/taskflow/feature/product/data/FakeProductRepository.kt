package com.tp.taskflow.feature.product.data

import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.product.domain.Product
import com.tp.taskflow.feature.product.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeProductRepository : ProductRepository {

    override fun search(query: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        delay(400)
        if (query.equals(ERROR_QUERY, ignoreCase = true)) {
            emit(Resource.Error("Search service unavailable"))
            return@flow
        }
        val filtered = catalog.filter { product ->
            query.isBlank() ||
                product.name.contains(query, ignoreCase = true) ||
                product.category.contains(query, ignoreCase = true)
        }
        emit(
            if (filtered.isEmpty()) Resource.Empty else Resource.Success(filtered)
        )
    }.flowOn(Dispatchers.IO)

    private companion object {
        const val ERROR_QUERY = "error"
        val catalog = listOf(
            Product("p-1", "Notebook Pro", "Stationery", 4.50),
            Product("p-2", "Task Stickers", "Stationery", 2.00),
            Product("p-3", "Focus Timer", "Gadgets", 18.00),
            Product("p-4", "Desk Lamp", "Gadgets", 32.00),
            Product("p-5", "Water Bottle", "Lifestyle", 12.00),
            Product("p-6", "Canvas Backpack", "Lifestyle", 45.00),
            Product("p-7", "Kotlin Handbook", "Books", 22.00),
            Product("p-8", "Android Workbook", "Books", 19.00),
            Product("p-9", "Wireless Mouse", "Gadgets", 16.00),
            Product("p-10", "Plant Pot", "Lifestyle", 9.00)
        )
    }
}
