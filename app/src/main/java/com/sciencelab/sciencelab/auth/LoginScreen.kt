package com.sciencelab.sciencelab.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sciencelab.sciencelab.preferences.Preferences
import com.sciencelab.sciencelab.R
import com.sciencelab.sciencelab.ScienceLabApplication

// Login screen composable
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

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
            text = "Learn Science",
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

        // Login button
        Button(
            onClick = {
                // Simple validation
                if (email.isEmpty() || password.isEmpty()) {
                    error = "Please enter email and password"
                } else {
                    // TODO: Call backend login endpoint
                    // For now, navigate to profile as if logged in
                    Preferences.saveString(Preferences.Keys.userId, email)
                    Preferences.saveString(Preferences.Keys.displayName, email.split("@")[0])
                    navController.navigate("home_dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                }
            },
            modifier = Modifier.width(300.dp).padding(top = 24.dp),
            enabled = error == null
        ) {
            Text(text = "Login")
        }

        // Divider
        Text(
            text = "or continue with",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Forgot password link
        TextButton(
            onClick = {
                // TODO: Show forgot password screen
            }
        ) {
            Text(text = "Forgot password?")
        }

        // Divider
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Signup link
        Button(
            onClick = {
                navController.navigate("signup")
            },
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Create new account")
        }

        // Guest access
        TextButton(
            onClick = {
                // Navigate as guest - skip auth
                Preferences.saveString(Preferences.Keys.userId, "guest")
                Preferences.saveString(Preferences.Keys.displayName, "Guest")
                navController.navigate("home_dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            }
        ) {
            Text(text = "Continue as Guest")
        }
    }
}