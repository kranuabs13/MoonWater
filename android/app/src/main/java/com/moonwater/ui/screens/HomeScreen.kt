package com.moonwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moonwater.R
import com.moonwater.data.StorageManager
import com.moonwater.utils.APKDownloadHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(storageManager: StorageManager, onNavigateToSettings: () -> Unit) {
    val dailyGoal by storageManager.dailyGoal.collectAsState(initial = 2000)
    val currentIntake by storageManager.currentIntake.collectAsState(initial = 0)
    val scope = rememberCoroutineScope()
    var showAmountDialog by remember { mutableStateOf(false) }

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
                        Text(stringResource(R.string.settings))
                    }
                    IconButton(onClick = { APKDownloadHelper.download(storageManager.context) }) {
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
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Simple Progress Circle
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = progress.coerceIn(0f, 1f),
                    modifier = Modifier.size(200.dp),
                    strokeWidth = 12.dp,
                    color = Color(0xFF2196F3)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.headlineLarge)
                    Text("$currentIntake / $dailyGoal", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Text(stringResource(R.string.remaining) + ": $remaining מ״ל", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { showAmountDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text(stringResource(R.string.drink_water), fontSize = 20.sp)
            }
        }
    }

    if (showAmountDialog) {
        AmountSelectionDialog(
            onDismiss = { showAmountDialog = false },
            onSelect = { amount ->
                scope.launch {
                    storageManager.updateIntake(amount)
                    showAmountDialog = false
                }
            }
        )
    }
}

@Composable
fun AmountSelectionDialog(onDismiss: () -> Unit, onSelect: (Int) -> Unit) {
    val amounts = listOf(150, 250, 330, 500)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("כמה שתית?") },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(amounts) { amount ->
                    Button(onClick = { onSelect(amount) }) {
                        Text("$amount מ״ל")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("ביטול") }
        }
    )
}
