package com.example.fallguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fallguard.screens.FallDetectedScreen
import com.example.fallguard.screens.HomeScreen
import com.example.fallguard.screens.OnboardingScreen
import com.example.fallguard.viewmodel.MainViewModel

object Routes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val FALL_DETECTED = "fall_detected"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                val startDestination = if (viewModel.isUserRegistered()) Routes.HOME else Routes.ONBOARDING

                NavHost(navController = navController, startDestination = startDestination) {

                    composable(Routes.ONBOARDING) {
                        OnboardingScreen(
                            initialData = viewModel.userData,
                            onSave = { newData ->
                                viewModel.registerOrUpdateUser(newData)
                                navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.ONBOARDING) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Routes.HOME) {
                        HomeScreen(
                            userName = viewModel.userData?.firstName ?: "Użytkowniku",
                            isTracking = viewModel.isTrackingActive,
                            onToggleTracking = { viewModel.toggleTracking() },
                            onEditProfile = {
                                navController.navigate(Routes.ONBOARDING)
                            },
                            onSimulateFall = {
                                navController.navigate(Routes.FALL_DETECTED)
                            }
                        )
                    }

                    composable(Routes.FALL_DETECTED) {
                        FallDetectedScreen(
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}