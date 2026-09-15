package com.tp.taskflow.feature.product.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query(
        """
        SELECT * FROM products
        WHERE :query = ''
           OR name LIKE '%' || :query || '%'
           OR category LIKE '%' || :query || '%'
        ORDER BY name
        """
    )
    fun pagingSource(query: String): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM products")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ProductEntity>)
}
