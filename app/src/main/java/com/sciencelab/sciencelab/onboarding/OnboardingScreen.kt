package com.sciencelab.sciencelab.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sciencelab.sciencelab.preferences.Preferences
import com.sciencelab.sciencelab.R

// Onboarding screen flow with 3 steps
@Composable
fun OnboardingScreen(
    navController: androidx.navigation.NavController,
    onComplete: () -> Unit
) {
    var step by remember { mutableIntStateOf(0) }

    when (step) {
        0 -> onboardingStep1(navController = navController) { step = 1 }
        1 -> onboardingStep2(navController = navController) { step = 2 }
        2 -> onboardingStep3(navController = navController) { onComplete() }
    }
}

// Step 1: Welcome screen
@Composable
private fun onboardingStep1(
    navController: androidx.navigation.NavController,
    onNext: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ScienceLab logo/title
        Text(
            text = "ScienceLab",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fontWeight(FontWeight.Bold).padding(vertical = 40.dp)
        )

        // Tagline
        Text(
            text = "Explore Science. Discover the Unknown. Learn by Experimenting.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        // Start button
        OutlinedButton(
            onClick = { onNext() },
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Text(text = "Get Started")
        }
    }
}

// Step 2: Features screen
@Composable
private fun onboardingStep2(
    navController: androidx.navigation.NavController,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Features title
        Text(
            text = "Features",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 40.dp)
        )

        // Feature cards
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            // Biology card
            FeatureCard(
                icon = "🔬",
                title = "Biology",
                description = "Cell theory, genetics, ecology & more"
            )

            // Chemistry card
            FeatureCard(
                icon = "🧪",
                title = "Chemistry",
                description = "Periodic table, reactions, elements"
            )

            // Physics card
            FeatureCard(
                icon = "⚛️",
                title = "Physics",
                description = "Formulas, calculations, motion & energy"
            )
        }

        // Spacer
        Spacer(modifier = Modifier.height(40.dp))

        // Continue button
        OutlinedButton(
            onClick = { onNext() },
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Text(text = "Next →")
        }
    }
}

// Step 3: Got it / complete screen
@Composable
private fun onboardingStep3(
    navController: androidx.navigation.NavController,
    onComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Checkmark/celebration
        Icon(
            imageVector = Icons.Flight.Takeoff,
            contentDescription = "ScienceLab",
            modifier = Modifier.size(80.dp).padding(vertical = 40.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Welcome to ScienceLab! 🎉",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Text(
            text = "Start your science learning journey today.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        // Skip to login/register button
        Button(
            onClick = { onComplete() },
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Text(text = "Skip to Login")
        }
    }
}

// Feature card composable
@Composable
private fun FeatureCard(
    icon: String,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.width(140.dp),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small,
        colors = CardColors(
            backgroundColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Science,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}