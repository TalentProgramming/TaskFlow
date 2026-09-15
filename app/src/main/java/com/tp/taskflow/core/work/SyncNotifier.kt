package com.tp.taskflow.core.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.tp.taskflow.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showSuccess() {
        val manager = context.getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(CHANNEL, "Sync", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
        val launch = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        manager.notify(
            1,
            NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("TaskFlow")
                .setContentText("Products updated")
                .setContentIntent(launch)
                .setAutoCancel(true)
                .build()
        )
    }

    private companion object {
        const val CHANNEL = "sync"
    }
}
