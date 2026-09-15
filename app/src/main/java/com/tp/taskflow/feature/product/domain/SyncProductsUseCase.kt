package com.tp.taskflow.feature.product.domain

import javax.inject.Inject

class SyncProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() {
        repository.refresh("")
    }
}
