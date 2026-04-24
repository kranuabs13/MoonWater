package com.moonwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    val currentIntake by storageManager.dailyIntake.collectAsState(initial = 0)
    val bottleSize by storageManager.bottleSize.collectAsState(initial = 750)
    val bottleLevel by storageManager.bottleLevel.collectAsState(initial = 1.0f)
    
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var pendingLevel by remember { mutableStateOf<Float?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val remaining = (dailyGoal - currentIntake).coerceAtLeast(0)
    val progress = (currentIntake.toFloat() / dailyGoal).coerceIn(0f, 1f)

    LaunchedEffect(Unit) {
        storageManager.checkDailyReset()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Black) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    TextButton(onClick = { APKDownloadHelper.download(context) }) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.download_apk), fontSize = 12.sp)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(stringResource(R.string.consumed), style = MaterialTheme.typography.labelMedium)
                        Text("$currentIntake מ״ל", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(stringResource(R.string.goal), style = MaterialTheme.typography.labelMedium)
                        Text("$dailyGoal מ״ל", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Bottle Interaction
            Text(
                "הקש או גרור על הבקבוק כדי לעדכן כמה שתית",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            BottleView(
                level = bottleLevel,
                onLevelChange = { newLevel ->
                    if (newLevel < bottleLevel) {
                        pendingLevel = newLevel
                        showConfirmDialog = true
                    } else if (newLevel > bottleLevel) {
                        // Accidental increase or intent to refill? 
                        // Just update visually for now or handle as refill
                        scope.launch { storageManager.updateBottleLevel(newLevel) }
                    }
                },
                onDragEnd = { finalLevel ->
                    if (finalLevel < bottleLevel) {
                        pendingLevel = finalLevel
                        showConfirmDialog = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Refill Button
            Button(
                onClick = { scope.launch { storageManager.refill() } },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("מילאתי בקבוק", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Progress Summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(stringResource(R.string.remaining) + ": $remaining מ״ל", style = MaterialTheme.typography.titleMedium)
                Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF2196F3), fontWeight = FontWeight.Black)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = Color(0xFF2196F3),
                trackColor = Color(0xFFE3F2FD)
            )
        }
    }

    if (showConfirmDialog && pendingLevel != null) {
        val diffPercent = bottleLevel - pendingLevel!!
        val drunkMl = (diffPercent * bottleSize).toInt()

        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("שתית מים?") },
            text = { Text("האם להוסיף $drunkMl מ״ל לצריכה היומית?") },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        storageManager.updateBottleLevel(pendingLevel!!)
                        showConfirmDialog = false
                    }
                }) {
                    Text("אישור")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("ביטול")
                }
            }
        )
    }
}
