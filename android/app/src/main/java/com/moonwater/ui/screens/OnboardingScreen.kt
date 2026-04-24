package com.moonwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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

@Composable
fun OnboardingScreen(storageManager: StorageManager, onComplete: () -> Unit) {
    var goal by remember { mutableStateOf("2000") }
    var bottleSize by remember { mutableStateOf("750") }
    var wakeTime by remember { mutableStateOf("07:00") }
    var sleepTime by remember { mutableStateOf("23:00") }
    var interval by remember { mutableStateOf("60") }
    var remindersEnabled by remember { mutableStateOf(true) }
    
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            stringResource(R.string.onboarding_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Text(
            "בואו נגדיר את הרגלי השתייה שלכם",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            label = { Text("נפח הבקבוק שלך (מ״ל)") },
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

        Spacer(modifier = Modifier.height(24.dp))

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
                    onComplete()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text(stringResource(R.string.start), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
