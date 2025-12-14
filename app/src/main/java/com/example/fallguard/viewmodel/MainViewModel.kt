package com.example.fallguard.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.fallguard.model.UserData
import com.example.fallguard.repository.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    var userData by mutableStateOf<UserData?>(null)
        private set

    var isTrackingActive by mutableStateOf(false)
        private set

    init {
        userData = userRepository.getUser()
    }

    fun registerOrUpdateUser(data: UserData) {
        userData = data
        userRepository.saveUser(data)
    }

    fun toggleTracking() {
        isTrackingActive = !isTrackingActive
        // TODO: Implement tracking logic here
    }

    fun isUserRegistered(): Boolean {
        return userRepository.isUserRegistered()
    }
}