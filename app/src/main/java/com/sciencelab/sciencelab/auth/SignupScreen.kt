package com.sciencelab.sciencelab.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sciencelab.sciencelab.preferences.Preferences
import com.sciencelab.sciencelab.R

// Signup screen composable
@Composable
fun SignupScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var gradeLevel by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var success by remember { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App title
        Text(
            text = "ScienceLab",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Tagline
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Error message
        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Success message
        if (success) {
            Text(
                text = "Account created successfully! Please login.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.success,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email address") },
            placeholder = { Text(text = "you@sciencelab.example") },
            modifier = Modifier.width(300.dp).padding(bottom = 8.dp),
            keyboardOptions = androidx.compose.ui.input.keyboard.KeyboardOptions(
                keyboardType = androidx.compose.ui.input.keyboard.KeyboardType.emailAddress
            )
        )

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            modifier = Modifier.width(300.dp).padding(top = 16.dp, bottom = 8.dp),
            isPassword = true,
            visualTransformation = androidx.compose.ui.visualPasswordVisualTransformation
        )

        // Confirm password field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirm password") },
            modifier = Modifier.width(300.dp).padding(top = 16.dp, bottom = 8.dp),
            isPassword = true,
            visualTransformation = androidx.compose.ui.visualPasswordVisualTransformation
        )

        // Display name field
        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text(text = "Display name") },
            modifier = Modifier.width(300.dp).padding(top = 16.dp, bottom = 8.dp)
        )

        // Grade level selector
        OutlinedTextField(
            value = gradeLevel,
            onValueChange = { gradeLevel = it },
            label = { Text(text = "Grade level (optional)") },
            modifier = Modifier.width(300.dp).padding(top = 16.dp, bottom = 8.dp)
        )

        // Signup button
        Button(
            onClick = {
                // Validation
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    error = "Please fill in all required fields"
                } else if (password != confirmPassword) {
                    error = "Passwords do not match"
                } else if (password.length < 6) {
                    error = "Password must be at least 6 characters"
                } else {
                    // TODO: Call backend registration endpoint
                    // For now, simulate success
                    success = true
                    // Save basic user info
                    Preferences.saveString(Preferences.Keys.userId, email)
                    Preferences.saveString(Preferences.Keys.displayName, displayName.isNotEmpty() ? displayName : email.split("@")[0])
                    // Navigate to login after short delay
                    // In real app, would wait for backend response
                    navController.navigate("login")
                }
            },
            modifier = Modifier.width(300.dp).padding(top = 24.dp),
            enabled = !error.contains("do not match") && !error.contains("characters")
        ) {
            Text(text = "Create account")
        }

        // Already have account link
        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier.width(300.dp).padding(top = 16.dp)
        ) {
            Text(text = "Already have an account? Login")
        }
    }
}