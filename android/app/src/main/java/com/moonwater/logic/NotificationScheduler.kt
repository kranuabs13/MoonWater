package com.moonwater.logic

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class NotificationScheduler(private val context: Context) {

    fun scheduleReminders(intervalMinutes: Int) {
        val reminderRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            intervalMinutes.toLong(), TimeUnit.MINUTES
        )
            .setConstraints(Constraints.NONE)
            .addTag("water_reminder")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "water_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            reminderRequest
        )
    }

    fun cancelReminders() {
        WorkManager.getInstance(context).cancelAllWorkByTag("water_reminder")
    }
}

class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        // Logic to check time bounds and send notification
        // (Simplified for this project code)
        return Result.success()
    }
}
