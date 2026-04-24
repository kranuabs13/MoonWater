package com.moonwater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moonwater.data.StorageManager
import com.moonwater.ui.screens.HomeScreen
import com.moonwater.ui.screens.OnboardingScreen
import com.moonwater.ui.screens.SettingsScreen
import com.moonwater.ui.theme.MoonWaterTheme

class MainActivity : ComponentActivity() {
    private lateinit var storageManager: StorageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageManager = StorageManager(this)

        setContent {
            MoonWaterTheme {
                // Force RTL for Hebrew
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val isOnboarded by storageManager.isOnboarded.collectAsState(initial = null)
                        
                        if (isOnboarded != null) {
                            AppNavigation(storageManager, isOnboarded == true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(storageManager: StorageManager, isOnboarded: Boolean) {
    val navController = rememberNavController()
    val startDestination = if (isOnboarded) "home" else "onboarding"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("onboarding") {
            OnboardingScreen(storageManager) {
                navController.navigate("home") {
                    popUpTo("onboarding") { inclusive = true }
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
                navController.popBackStack()
            })
        }
    }
}
