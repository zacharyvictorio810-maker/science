package com.sciencelab.sciencelab.network

import com.sciencelab.sciencelab.R
import com.sciencelab.sciencelab.preferences.Preferences
import com.sciencelab.sciencelab.ScienceLabApplication
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Authentication data models

// Request: Register
data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String? = null,
    val gradeLevel: String? = null,
    val preferredSubjects: String? = null
)

// Response: Register
data class RegisterResponse(
    val success: Boolean,
    val userId: String?,
    val token: String?,
    val message: String?
)

// Request: Login
data class LoginRequest(
    val email: String,
    val password: String
)

// Response: Login
data class LoginResponse(
    val success: Boolean,
    val userId: String?,
    val token: String?,
    val message: String?,
    val displayName: String?
)

// Request: Forgot password
data class ForgotPasswordRequest(
    val email: String
)

// Response: Forgot password
data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String?
)

// API service interface
interface AuthApiService {

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @GET("/users/me")
    suspend fun getCurrentUser(): Response<CurrentUserResponse>
}

// Current user response
data class CurrentUserResponse(
    val userId: String?,
    val email: String?,
    val displayName: String?,
    val gradeLevel: String?,
    val preferredSubjects: String?
)

// Helper functions for authentication

// Register user via backend
suspend fun registerUser(
    email: String,
    password: String,
    displayName: String? = null,
    gradeLevel: String? = null,
    preferredSubjects: String? = null
): Boolean {
    val service = ScienceLabApplication.authApiService!!
    val request = RegisterRequest(
        email = email,
        password = password,
        displayName = displayName,
        gradeLevel = gradeLevel,
        preferredSubjects = preferredSubjects
    )

    try {
        val response = service.register(request)
        if (response.isSuccessful && response.body()?.success == true) {
            // Save user info to DataStore
            if (response.body()?.userId != null) {
                Preferences.saveString(Preferences.Keys.userId, response.body().userId)
            }
            if (response.body()?.displayName != null) {
                Preferences.saveString(Preferences.Keys.displayName, response.body().displayName)
            }
            return true
        } else {
            // Show error message
            val errorMsg = response.body()?.message ?: "Registration failed"
            // TODO: Show Snackbar with error
            return false
        }
    } catch (e: Exception) {
        // TODO: Show network error message
        return false
    }
}

// Login user via backend
suspend fun loginUser(
    email: String,
    password: String
): Boolean {
    val service = ScienceLabApplication.authApiService!!
    val request = LoginRequest(email = email, password = password)

    try {
        val response = service.login(request)
        if (response.isSuccessful && response.body()?.success == true) {
            // Save user info to DataStore
            if (response.body()?.userId != null) {
                Preferences.saveString(Preferences.Keys.userId, response.body().userId)
            }
            if (response.body()?.displayName != null) {
                Preferences.saveString(Preferences.Keys.displayName, response.body().displayName)
            }
            // Navigate to home
            // TODO: navController.navigate("home_dashboard")
            return true
        } else {
            val errorMsg = response.body()?.message ?: "Login failed"
            // TODO: Show Snackbar with error
            return false
        }
    } catch (e: Exception) {
        // TODO: Show network error message
        return false
    }
}

// Send forgot password request
suspend fun sendForgotPasswordEmail(email: String): Boolean {
    val service = ScienceLabApplication.authApiService!!
    val request = ForgotPasswordRequest(email = email)

    try {
        val response = service.forgotPassword(request)
        if (response.isSuccessful && response.body()?.success == true) {
            // TODO: Show success message
            return true
        } else {
            val errorMsg = response.body()?.message ?: "Failed to send password reset"
            // TODO: Show error message
            return false
        }
    } catch (e: Exception) {
        // TODO: Show network error message
        return false
    }
}

// Get current user info from backend
suspend fun getCurrentUserInfo(): CurrentUserResponse? {
    val service = ScienceLabApplication.authApiService!!
    try {
        val response = service.getCurrentUser()
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    } catch (e: Exception) {
        return null
    }
}

// Check if user is logged in (using DataStore)
suspend fun isUserLoggedIn(): Boolean {
    val userId = Preferences.loadString(Preferences.Keys.userId)
    return userId != null && userId.isNotEmpty()
}

// Logout user
fun logoutUser() {
    // Clear DataStore preferences
    Preferences.saveString(Preferences.Keys.userId, "")
    Preferences.saveString(Preferences.Keys.displayName, "")
    // TODO: Call backend logout endpoint
    // TODO: Clear auth token
}