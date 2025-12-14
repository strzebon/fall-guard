package com.example.fallguard.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
    isTracking: Boolean,
    onToggleTracking: () -> Unit,
    onEditProfile: () -> Unit,
    onSimulateFall: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Fall Guard") },
                actions = {
                    IconButton(onClick = onEditProfile) {
                        Icon(Icons.Default.Edit, contentDescription = "Edytuj")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Person, null, Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Witaj, $userName!", style = MaterialTheme.typography.headlineMedium)

            Text(
                text = if (isTracking) "Ochrona AKTYWNA" else "Ochrona WYŁĄCZONA",
                color = if (isTracking) Color(0xFF4CAF50) else Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onToggleTracking,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isTracking) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.size(200.dp)
            ) {
                Text(if (isTracking) "STOP" else "START", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onSimulateFall,
                enabled = isTracking,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text("Symuluj Upadek")
            }
        }
    }
}