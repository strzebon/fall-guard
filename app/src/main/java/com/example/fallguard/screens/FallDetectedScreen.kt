package com.example.fallguard.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FallDetectedScreen(
    onCancel: () -> Unit,
) {
    var timer by remember { mutableIntStateOf(30) }
    var alertSent by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = alertSent) {
        if (!alertSent && timer > 0) {
            while (timer > 0) {
                delay(1000)
                timer--
            }
            alertSent = true
        }
    }

    Scaffold {
        Box(
            modifier = Modifier.padding(it).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (!alertSent) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        text = "Wykryto upadek!",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Wysłanie alertu za:",
                        fontSize = 20.sp
                    )
                    Text(
                        text = timer.toString(),
                        fontSize = 96.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = onCancel,
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Anuluj", fontSize = 22.sp)
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Alert Wysłany!",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("Kontakt alarmowy został powiadomiony.")
                    Button(onClick = onCancel) {
                        Text("OK")
                    }
                }
            }
        }
    }
}