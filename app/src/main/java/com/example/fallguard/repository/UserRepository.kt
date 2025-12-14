package com.example.fallguard.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.fallguard.model.UserData
import com.google.gson.Gson
import androidx.core.content.edit

class UserRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(userData: UserData) {
        val json = gson.toJson(userData)
        sharedPreferences.edit { putString("user_data", json) }
    }

    fun getUser(): UserData? {
        val json = sharedPreferences.getString("user_data", null)
        return gson.fromJson(json, UserData::class.java)
    }

    fun isUserRegistered(): Boolean {
        return sharedPreferences.contains("user_data")
    }
}
