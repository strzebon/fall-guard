package com.example.fallguard.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fallguard.model.UserData
import com.example.fallguard.notification.NotificationHelper
import com.example.fallguard.repository.UserRepository
import com.example.fallguard.sensor.FallDetector

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)
    private val notificationHelper = NotificationHelper(application)
    private val fallDetector = FallDetector(application) {
        fallDetected = true
    }

    var userData by mutableStateOf<UserData?>(null)
        private set

    var isTrackingActive by mutableStateOf(false)
        private set

    var fallDetected by mutableStateOf(false)
        private set

    init {
        userData = userRepository.getUser()
    }

    fun onFallHandled() {
        fallDetected = false
    }

    fun sendFallAlert() {
        userData?.let {
            notificationHelper.sendAlert(it)
        }
    }

    fun registerOrUpdateUser(data: UserData) {
        userData = data
        userRepository.saveUser(data)
    }

    fun toggleTracking() {
        isTrackingActive = !isTrackingActive
        if (isTrackingActive) {
            fallDetector.start()
        } else {
            fallDetector.stop()
        }
    }

    fun isUserRegistered(): Boolean {
        return userRepository.isUserRegistered()
    }
}
