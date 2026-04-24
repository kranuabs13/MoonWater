package com.moonwater.logic

import android.content.Context
import com.moonwater.data.StorageManager

object DailyResetManager {
    suspend fun checkAndReset(storageManager: StorageManager) {
        storageManager.checkDailyReset()
    }
}
