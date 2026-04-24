package com.moonwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moonwater.R
import com.moonwater.data.StorageManager
import com.moonwater.ui.components.BottleView
import com.moonwater.utils.APKDownloadHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(storageManager: StorageManager, onNavigateToSettings: () -> Unit) {
    val dailyGoal by storageManager.dailyGoal.collectAsState(initial = 2000)
    val currentIntake by storageManager.currentIntake.collectAsState(initial = 0)
    val bottleSize by storageManager.bottleSize.collectAsState(initial = 750)
    val bottleLevel by storageManager.bottleLevel.collectAsState(initial = 1.0f)
    val scope = rememberCoroutineScope()

    val progress = if (dailyGoal > 0) currentIntake.toFloat() / dailyGoal else 0f
    val remaining = (dailyGoal - currentIntake).coerceAtLeast(0)

    LaunchedEffect(Unit) {
        storageManager.checkDailyReset()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                    TextButton(onClick = { APKDownloadHelper.download(storageManager.context) }) {
                        Text(stringResource(R.string.download_apk), fontSize = 10.sp)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.goal) + ": $dailyGoal מ״ל", style = MaterialTheme.typography.titleLarge)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("הקש על הבקבוק כדי לעדכן כמה שתית", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(24.dp))
            
            BottleView(
                level = bottleLevel,
                onLevelChange = { newLevel ->
                    scope.launch {
                        storageManager.updateBottleLevel(newLevel, bottleSize)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.consumed) + ": $currentIntake מ״ל", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { scope.launch { storageManager.refillBottle() } }) {
                    Icon(Icons.Default.Refresh, contentDescription = "מילוי בקבוק")
                }
            }

            Text(stringResource(R.string.remaining) + ": $remaining מ״ל", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.outline)

            Spacer(modifier = Modifier.weight(1f))

            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("${(progress * 100).toInt()}% מהיעד היומי", style = MaterialTheme.typography.labelLarge)
        }
    }
}
