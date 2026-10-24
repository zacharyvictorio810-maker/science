package com.sciencelab.sciencelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavController
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.NavHostDefaults
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navGraph
import androidx.navigation.compose.navigation
import com.sciencelab.sciencelab.preferences.Preferences
import com.sciencelab.sciencelab.onboarding.OnboardingScreen
import com.sciencelab.sciencelab.theme.scienceLabTheme
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Route constants for navigation
object Routes {
    const val HomeDashboard = "home_dashboard"
    const val Biology = "biology"
    const val Chemistry = "chemistry"
    const val Physics = "physics"
    const val Anatomy = "anatomy"
    const val Genetics = "genetics"
    const val Biotechnology = "biotechnology"
    const val LabSafety = "lab_safety"
    const val Profile = "profile"
    const val Login = "login"
    const val Signup = "signup"
    const val AIChat = "ai_chat"
}

// Application class to hold global state
@HiltAndroidApp
class ScienceLabApplication : android.app.Application() {

    // DataStore instance for preferences
    val dataStore = androidx.datastore.dataStore(
        name = "science_lab_prefs",
        criteria = androidx.datastore.core.DefaultCriteria
    )

    override fun onCreate() {
        super.onCreate()
        // Initialize any global components here (e.g., database, analytics)
    }
}

// Main Activity with Jetpack Compose Navigation
class MainActivity : ComponentActivity() {
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Remember NavController programmatically (since we're defining navigation graph programmatically)
        navController = rememberNavController()
        setContent {
            // Apply theme based on stored preference
            scienceLabTheme {
                // A top-level Box composable that fills the screen
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Check if this is the first run
                    var firstRun by remember { mutableBooleanStateOf(true) }

                    withContext(Dispatchers.IO) {
                        firstRun.value = !Preferences.loadBoolean(Preferences.Keys.isFirstRun)
                    }

                    if (firstRun) {
                        // Show onboarding flow first
                        OnboardingScreen(
                            navController = navController,
                            onComplete = { Preferences.saveBoolean(Preferences.Keys.isFirstRun, false)
                                // Navigate to home after onboarding
                                navController?.navigate(Routes.HomeDashboard) {
                                    popUpTo(Routes.HomeDashboard) { inclusive = true }
                                }
                            }
                        )
                    } else {
                        // Normal navigation flow
                        NavHost(
                            navController = navController,
                            startDestination = Routes.HomeDashboard
                        ) {
                            // Home Dashboard
                            composable(Routes.HomeDashboard) {
                                HomeDashboardScreen(navController = navController)
                            }

                            // Biology Learning Center
                            composable(Routes.Biology) {
                                BiologyLearningCenter(navController = navController)
                            }

                            // Chemistry Learning Center
                            composable(Routes.Chemistry) {
                                ChemistryLearningCenter(navController = navController)
                            }

                            // Physics Learning Center
                            composable(Routes.Physics) {
                                PhysicsLearningCenter(navController = navController)
                            }

                            // Human Anatomy and Physiology
                            composable(Routes.Anatomy) {
                                AnatomyLearningCenter(navController = navController)
                            }

                            // Genetics Learning Center
                            composable(Routes.Genetics) {
                                GeneticsLearningCenter(navController = navController)
                            }

                            // Biotechnology Learning Center
                            composable(Routes.Biotechnology) {
                                BiotechnologyLearningCenter(navController = navController)
                            }

                            // Laboratory Safety Center
                            composable(Routes.LabSafety) {
                                LabSafetyCenter(navController = navController)
                            }

                            // Profile Screen
                            composable(Routes.Profile) {
                                ProfileScreen(navController = navController)
                            }

                            // Login Screen
                            composable(Routes.Login) {
                                LoginScreen(navController = navController)
                            }

                            // Signup Screen
                            composable(Routes.Signup) {
                                SignupScreen(navController = navController)
                            }

                            // AI Science Assistant Chat
                            composable(Routes.AIChat) {
                                AIScienceAssistantScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}