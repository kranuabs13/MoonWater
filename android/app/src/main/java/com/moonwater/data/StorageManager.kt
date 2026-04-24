package com.moonwater.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "settings")

class StorageManager(private val context: Context) {

    companion object {
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
        val WAKE_TIME = stringPreferencesKey("wake_time")
        val SLEEP_TIME = stringPreferencesKey("sleep_time")
        val INTERVAL = intPreferencesKey("interval")
        val DAILY_GOAL = intPreferencesKey("daily_goal")
        val REMINDERS_ENABLED = booleanPreferencesKey("reminders_enabled")
        val CURRENT_INTAKE = intPreferencesKey("current_intake")
        val LAST_RESET_DATE = stringPreferencesKey("last_reset_date")
    }

    val isOnboarded: Flow<Boolean> = context.dataStore.data.map { it[IS_ONBOARDED] ?: false }
    val wakeTime: Flow<String> = context.dataStore.data.map { it[WAKE_TIME] ?: "07:00" }
    val sleepTime: Flow<String> = context.dataStore.data.map { it[SLEEP_TIME] ?: "23:00" }
    val interval: Flow<Int> = context.dataStore.data.map { it[INTERVAL] ?: 60 }
    val dailyGoal: Flow<Int> = context.dataStore.data.map { it[DAILY_GOAL] ?: 2000 }
    val remindersEnabled: Flow<Boolean> = context.dataStore.data.map { it[REMINDERS_ENABLED] ?: true }
    val currentIntake: Flow<Int> = context.dataStore.data.map { it[CURRENT_INTAKE] ?: 0 }

    suspend fun saveOnboardingData(wake: String, sleep: String, interval: Int, goal: Int) {
        context.dataStore.edit { prefs ->
            prefs[WAKE_TIME] = wake
            prefs[SLEEP_TIME] = sleep
            prefs[INTERVAL] = interval
            prefs[DAILY_GOAL] = goal
            prefs[IS_ONBOARDED] = true
        }
    }

    suspend fun updateIntake(amount: Int) {
        context.dataStore.edit { prefs ->
            val current = prefs[CURRENT_INTAKE] ?: 0
            prefs[CURRENT_INTAKE] = current + amount
        }
    }

    suspend fun resetIntake() {
        context.dataStore.edit { prefs ->
            prefs[CURRENT_INTAKE] = 0
        }
    }

    suspend fun toggleReminders(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[REMINDERS_ENABLED] = enabled
        }
    }

    suspend fun checkDailyReset() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        context.dataStore.edit { prefs ->
            val lastReset = prefs[LAST_RESET_DATE]
            if (lastReset != today) {
                prefs[CURRENT_INTAKE] = 0
                prefs[LAST_RESET_DATE] = today
            }
        }
    }
}
