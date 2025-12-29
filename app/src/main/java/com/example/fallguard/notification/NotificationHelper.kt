package com.example.fallguard.notification

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import android.widget.Toast
import com.example.fallguard.model.UserData

class NotificationHelper(private val application: Application) {

    fun sendAlert(userData: UserData) {
        val message = "Wykryto upadek! Użytkownik ${userData.firstName} ${userData.lastName} może potrzebować pomocy."
        sendSms(userData.emergencyPhone, message)
        sendEmail(userData.emergencyEmail, "Alert: Wykryto upadek!", message)
    }

    private fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = application.getSystemService(SmsManager::class.java)
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(application, "Wysłano alert SMS.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(application, "Błąd wysyłania SMS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendEmail(emailAddress: String, subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            application.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(application, "Nie znaleziono aplikacji e-mail.", Toast.LENGTH_LONG).show()
        }
    }
}