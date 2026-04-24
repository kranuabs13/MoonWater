package com.moonwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moonwater.R
import com.moonwater.data.StorageManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(storageManager: StorageManager, onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    
    val savedGoal by storageManager.dailyGoal.collectAsState(initial = 2000)
    val savedBottle by storageManager.bottleSize.collectAsState(initial = 750)
    val savedWake by storageManager.wakeTime.collectAsState(initial = "07:00")
    val savedSleep by storageManager.sleepTime.collectAsState(initial = "23:00")
    val savedInterval by storageManager.interval.collectAsState(initial = 60)
    val savedReminders by storageManager.remindersEnabled.collectAsState(initial = true)

    var goal by remember { mutableStateOf(savedGoal.toString()) }
    var bottleSize by remember { mutableStateOf(savedBottle.toString()) }
    var wakeTime by remember { mutableStateOf(savedWake) }
    var sleepTime by remember { mutableStateOf(savedSleep) }
    var interval by remember { mutableStateOf(savedInterval.toString()) }
    var remindersEnabled by remember { mutableStateOf(savedReminders) }

    LaunchedEffect(savedGoal) { goal = savedGoal.toString() }
    LaunchedEffect(savedBottle) { bottleSize = savedBottle.toString() }
    LaunchedEffect(savedWake) { wakeTime = savedWake }
    LaunchedEffect(savedSleep) { sleepTime = savedSleep }
    LaunchedEffect(savedInterval) { interval = savedInterval.toString() }
    LaunchedEffect(savedReminders) { remindersEnabled = savedReminders }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            OutlinedTextField(
                value = goal,
                onValueChange = { goal = it },
                label = { Text(stringResource(R.string.water_goal_ml)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bottleSize,
                onValueChange = { bottleSize = it },
                label = { Text("נפח הבקבוק (מ״ל)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = wakeTime,
                    onValueChange = { wakeTime = it },
                    label = { Text(stringResource(R.string.wake_up_time)) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = sleepTime,
                    onValueChange = { sleepTime = it },
                    label = { Text(stringResource(R.string.sleep_time)) },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = interval,
                onValueChange = { interval = it },
                label = { Text(stringResource(R.string.interval)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.reminders_enabled), style = MaterialTheme.typography.bodyLarge)
                Switch(checked = remindersEnabled, onCheckedChange = { remindersEnabled = it })
            }

            Divider()

            TextButton(
                onClick = { scope.launch { storageManager.resetDaily() } },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.reset_progress), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        storageManager.saveSettings(
                            goal.toIntOrNull() ?: 2000,
                            bottleSize.toIntOrNull() ?: 750,
                            wakeTime,
                            sleepTime,
                            interval.toIntOrNull() ?: 60,
                            remindersEnabled
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(stringResource(R.string.save), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
