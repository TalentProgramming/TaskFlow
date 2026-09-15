package com.tp.taskflow.feature.product.data

import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.network.TaskFlowApi
import com.tp.taskflow.feature.product.domain.Product
import com.tp.taskflow.feature.product.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: TaskFlowApi
) : ProductRepository {

    override fun search(query: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        try {
            val filtered = api.products(query).map { it.toDomain() }
            emit(
                if (filtered.isEmpty()) Resource.Empty else Resource.Success(filtered)
            )
        } catch (error: HttpException) {
            emit(
                Resource.Error(
                    when (error.code()) {
                        500 -> "Search service unavailable"
                        else -> "Could not load products (${error.code()})"
                    }
                )
            )
        } catch (error: Exception) {
            emit(Resource.Error(error.message ?: "Could not load products"))
        }
    }.flowOn(Dispatchers.IO)
}
