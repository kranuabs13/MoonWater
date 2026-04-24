package com.moonwater.logic

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.moonwater.MainActivity
import com.moonwater.R
import com.moonwater.data.StorageManager
import kotlinx.coroutines.flow.first
import java.util.*

class ReminderWorker(val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    companion object {
        const val CHANNEL_ID = "moonwater_reminders"
        const val NOTIFICATION_ID = 1234
    }

    override suspend fun doWork(): Result {
        val storageManager = StorageManager(context)
        
        val enabled = storageManager.remindersEnabled.first()
        if (!enabled) return Result.success()

        val wake = storageManager.wakeTime.first()
        val sleep = storageManager.sleepTime.first()

        if (isCurrentTimeInWindow(wake, sleep)) {
            sendWaterNotification()
        }

        return Result.success()
    }

    private fun isCurrentTimeInWindow(wake: String, sleep: String): Boolean {
        val now = Calendar.getInstance()
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

        val wakeParts = wake.split(":").map { it.toInt() }
        val sleepParts = sleep.split(":").map { it.toInt() }
        
        val wakeMinutes = wakeParts[0] * 60 + wakeParts[1]
        val sleepMinutes = sleepParts[0] * 60 + sleepParts[1]

        return if (wakeMinutes < sleepMinutes) {
            // Normal internal (e.g. 07:00 to 23:00)
            currentMinutes in wakeMinutes..sleepMinutes
        } else {
            // Crosses midnight (e.g. 22:00 to 06:00)
            currentMinutes >= wakeMinutes || currentMinutes <= sleepMinutes
        }
    }

    private fun sendWaterNotification() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "MoonWater Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders to drink water"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // In a real app, use a custom water drop icon
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
