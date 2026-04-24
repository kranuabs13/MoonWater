package com.moonwater.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moonwater.data.StorageManager
import com.moonwater.logic.NotificationScheduler
import com.moonwater.ui.screens.HomeScreen
import com.moonwater.ui.screens.OnboardingScreen
import com.moonwater.ui.screens.SettingsScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    storageManager: StorageManager, 
    scheduler: NotificationScheduler,
    isOnboarded: Boolean
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val startDestination = if (isOnboarded) "home" else "onboarding"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("onboarding") {
            OnboardingScreen(storageManager) {
                scope.launch {
                    val enabled = storageManager.remindersEnabled.first()
                    if (enabled) {
                        val interval = storageManager.interval.first()
                        scheduler.scheduleReminders(interval)
                    }
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            }
        }
        composable("home") {
            HomeScreen(storageManager, onNavigateToSettings = {
                navController.navigate("settings")
            })
        }
        composable("settings") {
            SettingsScreen(storageManager, onBack = {
                scope.launch {
                    val enabled = storageManager.remindersEnabled.first()
                    if (enabled) {
                        val interval = storageManager.interval.first()
                        scheduler.scheduleReminders(interval)
                    } else {
                        scheduler.cancelReminders()
                    }
                    navController.popBackStack()
                }
            })
        }
    }
}
