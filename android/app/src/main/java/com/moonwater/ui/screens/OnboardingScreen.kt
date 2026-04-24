package com.moonwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
    var wakeTime by remember { mutableStateOf("07:00") }
    var sleepTime by remember { mutableStateOf("23:00") }
    var interval by remember { mutableStateOf("60") }
    var dailyGoal by remember { mutableStateOf("2000") }
    var bottleSize by remember { mutableStateOf("750") }
    
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            stringResource(R.string.onboarding_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(12.dp))

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
            label = { Text("מה נפח הבקבוק שלך? (מ״ל)") },
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
                    onComplete()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(stringResource(R.string.start), fontSize = 18.sp)
        }
    }
}
