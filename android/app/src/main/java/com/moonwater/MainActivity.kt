package com.moonwater

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moonwater.data.StorageManager
import com.moonwater.logic.NotificationScheduler
import com.moonwater.ui.screens.HomeScreen
import com.moonwater.ui.screens.OnboardingScreen
import com.moonwater.ui.screens.SettingsScreen
import com.moonwater.ui.theme.MoonWaterTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var storageManager: StorageManager
    private lateinit var notificationScheduler: NotificationScheduler

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, trigger a schedule check
            applySchedulerSync()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageManager = StorageManager(this)
        notificationScheduler = NotificationScheduler(this)

        checkNotificationPermission()
        
        // Initial sync of scheduler
        applySchedulerSync()

        setContent {
            MoonWaterTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        var isOnboardedState by remember { mutableStateOf<Boolean?>(null) }
                        
                        LaunchedEffect(Unit) {
                            storageManager.checkDailyReset()
                            isOnboardedState = storageManager.isOnboarded.first()
                        }
                        
                        if (isOnboardedState != null) {
                            AppNavigation(storageManager, notificationScheduler, isOnboardedState!!)
                        }
                    }
                }
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    
    private fun applySchedulerSync() {
        lifecycleScope.launch {
            val enabled = storageManager.remindersEnabled.first()
            if (enabled) {
                val interval = storageManager.interval.first()
                notificationScheduler.scheduleReminders(interval)
            } else {
                notificationScheduler.cancelReminders()
            }
        }
    }
}

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
