package com.moonwater.logic

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class NotificationScheduler(private val context: Context) {

    companion object {
        private const val WORK_NAME = "water_reminder_work"
        private const val TAG = "water_reminder"
    }

    fun scheduleReminders(intervalMinutes: Int) {
        val workManager = WorkManager.getInstance(context)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        // WorkManager minimum periodic interval is 15 minutes
        val effectiveInterval = intervalMinutes.toLong().coerceAtLeast(15L)

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            effectiveInterval, TimeUnit.MINUTES
        )
            .addTag(TAG)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun cancelReminders() {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}
