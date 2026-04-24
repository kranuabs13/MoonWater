package com.moonwater.logic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.moonwater.R
import com.moonwater.data.StorageManager
import kotlinx.coroutines.flow.first
import java.util.*

class ReminderWorker(val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    companion object {
        const val CHANNEL_ID = "water_reminders"
        const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        val storageManager = StorageManager(context)
        val remindersEnabled = storageManager.remindersEnabled.first()
        
        if (!remindersEnabled) return Result.success()

        val wakeTime = storageManager.wakeTime.first() // e.g., "07:00"
        val sleepTime = storageManager.sleepTime.first()

        if (isWithinActiveHours(wakeTime, sleepTime)) {
            sendNotification()
        }

        return Result.success()
    }

    private fun isWithinActiveHours(wake: String, sleep: String): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMin = calendar.get(Calendar.MINUTE)
        val currentTimeInMin = currentHour * 60 + currentMin

        val wakeParts = wake.split(":").map { it.toInt() }
        val sleepParts = sleep.split(":").map { it.toInt() }

        val wakeMinTotal = wakeParts[0] * 60 + wakeParts[1]
        val sleepMinTotal = sleepParts[0] * 60 + sleepParts[1]

        return currentTimeInMin in wakeMinTotal..sleepMinTotal
    }

    private fun sendNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Water Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with app icon in real project
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}
