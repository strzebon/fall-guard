package com.example.fallguard.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fallguard.model.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    initialData: UserData?,
    onSave: (UserData) -> Unit
) {
    var firstName by remember { mutableStateOf(initialData?.firstName ?: "") }
    var lastName by remember { mutableStateOf(initialData?.lastName ?: "") }
    var age by remember { mutableStateOf(initialData?.age ?: "") }
    var height by remember { mutableStateOf(initialData?.height ?: "") }
    var weight by remember { mutableStateOf(initialData?.weight ?: "") }
    var phone by remember { mutableStateOf(initialData?.emergencyPhone ?: "") }
    var email by remember { mutableStateOf(initialData?.emergencyEmail ?: "") }

    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var ageError by remember { mutableStateOf<String?>(null) }
    var heightError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val isFormValid by derivedStateOf {
        firstName.isNotBlank() && lastName.isNotBlank() &&
                (age.isNotBlank() && age.all { it.isDigit() }) &&
                (height.isNotBlank() && height.all { it.isDigit() }) &&
                (weight.isNotBlank() && weight.all { it.isDigit() }) &&
                (phone.isNotBlank() && phone.all { it.isDigit() }) &&
                (email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun validateAllFields() {
        firstNameError = if (firstName.isBlank()) "Pole jest wymagane" else null
        lastNameError = if (lastName.isBlank()) "Pole jest wymagane" else null
        ageError = when {
            age.isBlank() -> "Wymagane"
            !age.all { it.isDigit() } -> "Tylko cyfry"
            else -> null
        }
        heightError = when {
            height.isBlank() -> "Wymagane"
            !height.all { it.isDigit() } -> "Tylko cyfry"
            else -> null
        }
        weightError = when {
            weight.isBlank() -> "Wymagane"
            !weight.all { it.isDigit() } -> "Tylko cyfry"
            else -> null
        }
        phoneError = when {
            phone.isBlank() -> "Pole jest wymagane"
            !phone.all { it.isDigit() } -> "Nieprawidłowy numer"
            else -> null
        }
        emailError = when {
            email.isBlank() -> "Pole jest wymagane"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Nieprawidłowy email"
            else -> null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (initialData == null) "Rejestracja" else "Edycja Profilu") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Dane Użytkownika", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

            OutlinedTextField(
                value = firstName, onValueChange = { firstName = it; firstNameError = if (it.isBlank()) "Pole jest wymagane" else null },
                label = { Text("Imię", fontSize = 14.sp) }, modifier = Modifier.fillMaxWidth(),
                isError = firstNameError != null,
                supportingText = { firstNameError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = lastName, onValueChange = { lastName = it; lastNameError = if (it.isBlank()) "Pole jest wymagane" else null },
                label = { Text("Nazwisko", fontSize = 14.sp) }, modifier = Modifier.fillMaxWidth(),
                isError = lastNameError != null,
                supportingText = { lastNameError?.let { Text(it) } }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = age, onValueChange = { age = it; ageError = if (it.isBlank()) "Wymagane" else if (!it.all { c -> c.isDigit() }) "Tylko cyfry" else null },
                    label = { Text("Wiek", fontSize = 14.sp) }, modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = ageError != null,
                    supportingText = { ageError?.let { Text(it) } }
                )
                OutlinedTextField(
                    value = height, onValueChange = { height = it; heightError = if (it.isBlank()) "Wymagane" else if (!it.all { c -> c.isDigit() }) "Tylko cyfry" else null },
                    label = { Text("Wzrost [cm]", fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = heightError != null,
                    supportingText = { heightError?.let { Text(it) } }
                )
                OutlinedTextField(
                    value = weight, onValueChange = { weight = it; weightError = if (it.isBlank()) "Wymagane" else if (!it.all { c -> c.isDigit() }) "Tylko cyfry" else null },
                    label = { Text("Waga [kg]", fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = weightError != null,
                    supportingText = { weightError?.let { Text(it) } }
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )

            Text("Kontakt Alarmowy", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = phone, onValueChange = { phone = it; phoneError = if (it.isBlank()) "Pole jest wymagane" else if (!it.all { c -> c.isDigit() }) "Nieprawidłowy numer" else null },
                label = { Text("Telefon alarmowy", fontSize = 14.sp) }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phoneError != null,
                supportingText = { phoneError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = email, onValueChange = { email = it; emailError = if (it.isBlank()) "Pole jest wymagane" else if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Nieprawidłowy email" else null },
                label = { Text("E-mail opiekuna", fontSize = 14.sp) }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    validateAllFields()
                    if(isFormValid) {
                        onSave(UserData(firstName, lastName, age, height, weight, phone, email))
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(if (initialData == null) "Zarejestruj" else "Zapisz Zmiany")
            }
        }
    }
}