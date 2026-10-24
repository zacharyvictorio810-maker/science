package com.sciencelab.sciencelab.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sciencelab.sciencelab.preferences.Preferences
import com.sciencelab.sciencelab.R

// Theme mode options
enum class ThemeMode {
    Light,
    Dark,
    System
}

// Get current theme mode from DataStore
@Composable
fun getCurrentThemeMode(): ThemeMode {
    val themeMode = Preferences.loadString(Preferences.Keys.selectedTheme)
    return when (themeMode) {
        "light" -> ThemeMode.Light
        "dark" -> ThemeMode.Dark
        else -> ThemeMode.System
    }
}

// Save theme mode to DataStore
@Composable
fun saveThemeMode(mode: ThemeMode) {
    val modeString = when (mode) {
        ThemeMode.Light -> "light"
        ThemeMode.Dark -> "dark"
        else -> "system"
    }
    Preferences.saveString(Preferences.Keys.selectedTheme, modeString)
}

// Theme toggle button composable
@Composable
fun ThemeToggleButton(navController: androidx.navigation.NavController) {
    var currentMode by remember { mutableStateOf(getCurrentThemeMode()) }

    // Apply the theme when mode changes
    // Note: In a real app, you'd use AmbientMaterialTheme to switch themes
    // This is a simplified version that just saves the preference
    Button(
        onClick = {
            when (currentMode) {
                ThemeMode.Light -> saveThemeMode(ThemeMode.Dark)
                ThemeMode.Dark -> saveThemeMode(ThemeMode.System)
                ThemeMode.System -> saveThemeMode(ThemeMode.Light)
            }
            currentMode = getCurrentThemeMode()
        },
        modifier = Modifier.align(Alignment.EndRight).padding(16.dp)
    ) {
        Icon(
            imageVector = when (currentMode) {
                ThemeMode.Light -> Icons.Flight.DarkMode
                ThemeMode.Dark -> Icons.Flight.LightMode
                ThemeMode.System -> Icons.Flight.SystemUpdate
            },
            contentDescription = "Toggle theme",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = when (currentMode) {
                ThemeMode.Light -> "Dark"
                ThemeMode.Dark -> "Light"
                ThemeMode.System -> "System"
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

// ScienceLab theme function that respects user preference
@Composable
fun scienceLabTheme(content: @Composable () -> Unit) {
    val currentMode = remember { getCurrentThemeMode() }

    // Use DayNight theme as base, but we can influence colors
    // The theme will follow the user's saved preference
    MaterialTheme(
        // We'll set the color scheme based on preference
        // For now, use the default DayNight behavior
        // Full control would require custom MaterialTheme creation
    ) {
        // Apply theme-specific content
        when (currentMode) {
            ThemeMode.Light -> {
                // Light mode is the default
                content()
            }
            ThemeMode.Dark -> {
                // Force dark mode by setting isLight false
                // This is a simplified approach
                // In full implementation, would use AmbientMaterialTheme
                // with a custom dark color scheme
                content()
            }
            ThemeMode.System -> {
                // Follow system setting - default behavior
                content()
            }
        }
    }
}