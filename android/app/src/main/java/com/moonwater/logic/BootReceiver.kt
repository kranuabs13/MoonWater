package com.moonwater.logic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.moonwater.data.StorageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val storageManager = StorageManager(context)
            val scheduler = NotificationScheduler(context)
            
            CoroutineScope(Dispatchers.IO).launch {
                val enabled = storageManager.remindersEnabled.first()
                if (enabled) {
                    val interval = storageManager.interval.first()
                    scheduler.scheduleReminders(interval)
                }
            }
        }
    }
}
