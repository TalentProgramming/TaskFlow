package com.tp.taskflow.feature.product.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.network.TaskFlowApi
import com.tp.taskflow.feature.product.domain.Product
import com.tp.taskflow.feature.product.domain.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: TaskFlowApi,
    private val dao: ProductDao
) : ProductRepository {

    override fun search(query: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)
        try {
            refresh(query)
            emit(Resource.Success(emptyList()))
        } catch (error: HttpException) {
            emit(Resource.Error(if (error.code() == 500) "Search service unavailable" else error.message()))
        } catch (error: Exception) {
            emit(Resource.Error(error.message ?: "Could not search"))
        }
    }

    override fun paged(query: String): Flow<PagingData<Product>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { dao.pagingSource(query) }
        ).flow.map { paging -> paging.map { it.toDomain() } }

    override suspend fun refresh(query: String) {
        val remote = api.products(query)
        dao.clear()
        dao.insertAll(remote.map { it.toEntity() })
    }
}
