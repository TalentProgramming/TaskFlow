package com.tp.taskflow.feature.product.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.core.database.TaskFlowDatabase
import com.tp.taskflow.core.network.TaskFlowApi
import com.tp.taskflow.feature.product.domain.Product
import com.tp.taskflow.feature.product.domain.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: TaskFlowApi,
    private val dao: ProductDao,
    private val database: TaskFlowDatabase
) : ProductRepository {

    override fun search(query: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Success(emptyList()))
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun paged(query: String): Flow<PagingData<Product>> =
        Pager(
            config = PagingConfig(
                pageSize = ClassroomProductCatalog.PAGE_SIZE,
                initialLoadSize = ClassroomProductCatalog.PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            remoteMediator = ProductRemoteMediator(query, api, dao, database),
            pagingSourceFactory = { dao.pagingSource(query) }
        ).flow.map { paging -> paging.map { it.toDomain() } }

    override suspend fun refresh(query: String) {
        val remote = api.products(query, 1)
        dao.clear()
        dao.insertAll(remote.map { it.toEntity() })
    }
}
