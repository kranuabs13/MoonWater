package com.moonwater.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "moonwater_settings")

class StorageManager(private val context: Context) {

    companion object {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val DAILY_GOAL_ML = intPreferencesKey("daily_goal_ml")
        val BOTTLE_SIZE_ML = intPreferencesKey("bottle_size_ml")
        val CURRENT_BOTTLE_LEVEL = floatPreferencesKey("current_bottle_level")
        val DAILY_INTAKE_ML = intPreferencesKey("daily_intake_ml")
        val WAKE_TIME = stringPreferencesKey("wake_time")
        val SLEEP_TIME = stringPreferencesKey("sleep_time")
        val REMINDER_INTERVAL_MINS = intPreferencesKey("reminder_interval_mins")
        val REMINDERS_ENABLED = booleanPreferencesKey("reminders_enabled")
        val LAST_RESET_DATE = stringPreferencesKey("last_reset_date")
    }

    val isOnboarded: Flow<Boolean> = context.dataStore.data.map { it[ONBOARDING_COMPLETED] ?: false }
    val dailyGoal: Flow<Int> = context.dataStore.data.map { it[DAILY_GOAL_ML] ?: 2000 }
    val bottleSize: Flow<Int> = context.dataStore.data.map { it[BOTTLE_SIZE_ML] ?: 750 }
    val bottleLevel: Flow<Float> = context.dataStore.data.map { it[CURRENT_BOTTLE_LEVEL] ?: 1.0f }
    val dailyIntake: Flow<Int> = context.dataStore.data.map { it[DAILY_INTAKE_ML] ?: 0 }
    val wakeTime: Flow<String> = context.dataStore.data.map { it[WAKE_TIME] ?: "07:00" }
    val sleepTime: Flow<String> = context.dataStore.data.map { it[SLEEP_TIME] ?: "23:00" }
    val interval: Flow<Int> = context.dataStore.data.map { it[REMINDER_INTERVAL_MINS] ?: 60 }
    val remindersEnabled: Flow<Boolean> = context.dataStore.data.map { it[REMINDERS_ENABLED] ?: true }

    suspend fun saveSettings(
        goal: Int,
        bottle: Int,
        wake: String,
        sleep: String,
        intervalMins: Int,
        reminders: Boolean
    ) {
        context.dataStore.edit { prefs ->
            prefs[DAILY_GOAL_ML] = goal
            prefs[BOTTLE_SIZE_ML] = bottle
            prefs[WAKE_TIME] = wake
            prefs[SLEEP_TIME] = sleep
            prefs[REMINDER_INTERVAL_MINS] = intervalMins
            prefs[REMINDERS_ENABLED] = reminders
            prefs[ONBOARDING_COMPLETED] = true
        }
    }

    suspend fun updateBottleLevel(newLevel: Float) {
        context.dataStore.edit { prefs ->
            val oldLevel = prefs[CURRENT_BOTTLE_LEVEL] ?: 1.0f
            val bottleSize = prefs[BOTTLE_SIZE_ML] ?: 750
            
            // Only add intake if level decreased (drank)
            if (newLevel < oldLevel) {
                val diffPercent = oldLevel - newLevel
                val addedMl = (diffPercent * bottleSize).toInt()
                val currentIntake = prefs[DAILY_INTAKE_ML] ?: 0
                prefs[DAILY_INTAKE_ML] = currentIntake + addedMl
            }
            prefs[CURRENT_BOTTLE_LEVEL] = newLevel
        }
    }

    suspend fun refill() {
        context.dataStore.edit { prefs ->
            prefs[CURRENT_BOTTLE_LEVEL] = 1.0f
        }
    }

    suspend fun resetDaily() {
        context.dataStore.edit { prefs ->
            prefs[DAILY_INTAKE_ML] = 0
            prefs[CURRENT_BOTTLE_LEVEL] = 1.0f
            prefs[LAST_RESET_DATE] = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        }
    }

    suspend fun checkDailyReset() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        context.dataStore.edit { prefs ->
            val lastReset = prefs[LAST_RESET_DATE]
            if (lastReset != today) {
                prefs[DAILY_INTAKE_ML] = 0
                prefs[CURRENT_BOTTLE_LEVEL] = 1.0f
                prefs[LAST_RESET_DATE] = today
            }
        }
    }
}
