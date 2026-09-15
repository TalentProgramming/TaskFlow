package com.tp.taskflow.core.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tp.taskflow.feature.product.domain.SyncProductsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncProducts: SyncProductsUseCase,
    private val notifier: SyncNotifier
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            syncProducts()
            notifier.showSuccess()
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
