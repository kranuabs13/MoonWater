package com.moonwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.moonwater.R
import com.moonwater.data.StorageManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(storageManager: StorageManager, onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    
    val wakeTimeFlow = storageManager.wakeTime.collectAsState(initial = "07:00")
    val sleepTimeFlow = storageManager.sleepTime.collectAsState(initial = "23:00")
    val intervalFlow = storageManager.interval.collectAsState(initial = 60)
    val dailyGoalFlow = storageManager.dailyGoal.collectAsState(initial = 2000)
    val bottleSizeFlow = storageManager.bottleSize.collectAsState(initial = 750)
    val remindersEnabledFlow = storageManager.remindersEnabled.collectAsState(initial = true)

    var wakeTime by remember { mutableStateOf(wakeTimeFlow.value) }
    var sleepTime by remember { mutableStateOf(sleepTimeFlow.value) }
    var interval by remember { mutableStateOf(intervalFlow.value.toString()) }
    var dailyGoal by remember { mutableStateOf(dailyGoalFlow.value.toString()) }
    var bottleSize by remember { mutableStateOf(bottleSizeFlow.value.toString()) }
    var remindersEnabled by remember { mutableStateOf(remindersEnabledFlow.value) }

    LaunchedEffect(wakeTimeFlow.value) { wakeTime = wakeTimeFlow.value }
    LaunchedEffect(sleepTimeFlow.value) { sleepTime = sleepTimeFlow.value }
    LaunchedEffect(intervalFlow.value) { interval = intervalFlow.value.toString() }
    LaunchedEffect(dailyGoalFlow.value) { dailyGoal = dailyGoalFlow.value.toString() }
    LaunchedEffect(bottleSizeFlow.value) { bottleSize = bottleSizeFlow.value.toString() }
    LaunchedEffect(remindersEnabledFlow.value) { remindersEnabled = remindersEnabledFlow.value }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = wakeTime,
                onValueChange = { wakeTime = it },
                label = { Text(stringResource(R.string.wake_up_time)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = sleepTime,
                onValueChange = { sleepTime = it },
                label = { Text(stringResource(R.string.sleep_time)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = interval,
                onValueChange = { interval = it },
                label = { Text(stringResource(R.string.interval)) },
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
            OutlinedTextField(
                value = dailyGoal,
                onValueChange = { dailyGoal = it },
                label = { Text(stringResource(R.string.water_goal_ml)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text(stringResource(R.string.reminders_enabled))
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = remindersEnabled, onCheckedChange = { remindersEnabled = it })
            }

            Button(
                onClick = {
                    scope.launch {
                        storageManager.resetIntake()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.reset_progress))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        storageManager.saveOnboardingData(
                            wakeTime, 
                            sleepTime, 
                            interval.toIntOrNull() ?: 60, 
                            dailyGoal.toIntOrNull() ?: 2000,
                            bottleSize.toIntOrNull() ?: 750
                        )
                        storageManager.toggleReminders(remindersEnabled)
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}
