package com.tp.taskflow.feature.product.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tp.taskflow.core.database.TaskFlowDatabase
import com.tp.taskflow.core.network.TaskFlowApi

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val query: String,
    private val api: TaskFlowApi,
    private val dao: ProductDao,
    private val database: TaskFlowDatabase
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val loaded = dao.count(query)
                    if (loaded == 0) 1 else loaded / ClassroomProductCatalog.PAGE_SIZE + 1
                }
            }
            val remote = api.products(query, page)
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.clear()
                }
                if (remote.isNotEmpty()) {
                    dao.insertAll(remote.map { it.toEntity() })
                }
            }
            MediatorResult.Success(endOfPaginationReached = remote.size < ClassroomProductCatalog.PAGE_SIZE)
        } catch (error: Exception) {
            MediatorResult.Error(error)
        }
    }
}
