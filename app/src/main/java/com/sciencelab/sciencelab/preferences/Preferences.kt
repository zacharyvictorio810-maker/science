package com.sciencelab.sciencelab.preferences

import androidx.datastore.core.Serializer
import androidx.datastore.datastore
import androidx.datastore.preferencesBooleanSerializer
import androidx.datastore.preferencesIntSerializer
import androidx.datastore.preferencesStringSerializer
import com.sciencelab.sciencelab.ScienceLabApplication
import java.io.IOException

object Preferences {

    // Serializer for Boolean values
    private val BooleanSerializer = preferencesBooleanSerializer()

    // Serializer for Int values
    private val IntSerializer = preferencesIntSerializer()

    // Serializer for String values
    private val StringSerializer = preferencesStringSerializer()

    // Keys for preferences
    object Keys {
        val isFirstRun by preferencesKey<Boolean>(key = "is_first_run")
        val selectedTheme by preferencesKey<String>(key = "selected_theme")
        val userId by preferencesKey<String>(key = "user_id")
        val displayName by preferencesKey<String>(key = "display_name")
        val gradeLevel by preferencesKey<String>(key = "grade_level")
        val preferredSubjects by preferencesKey<String>(key = "preferred_subjects")
        val learningStreak by preferencesKey<Int>(key = "learning_streak")
        val totalLearningTime by preferencesKey<Long>(key = "total_learning_time")
        val lastLogin by preferencesKey<Long>(key = "last_login")
    }

    // Load preferences from DataStore
    suspend fun loadBoolean(key: preferencesKey<Boolean>): Boolean {
        return ScienceLabApplication.dataStore
            .get(key, defaultValue = true, serializer = BooleanSerializer)
    }

    suspend fun loadString(key: preferencesKey<String>): String {
        return ScienceLabApplication.dataStore
            .get(key, defaultValue = "", serializer = StringSerializer)
    }

    suspend fun loadInt(key: preferencesKey<Int>): Int {
        return ScienceLabApplication.dataStore
            .get(key, defaultValue = 0, serializer = IntSerializer)
    }

    suspend fun loadLong(key: preferencesKey<Long>): Long {
        return ScienceLabApplication.dataStore
            .get(key, defaultValue = 0L, serializer = preferencesLongSerializer())
    }

    // Save preferences to DataStore
    suspend fun saveBoolean(key: preferencesKey<Boolean>, value: Boolean) {
        ScienceLabApplication.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun saveString(key: preferencesKey<String>, value: String) {
        ScienceLabApplication.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun saveInt(key: preferencesKey<Int>, value: Int) {
        ScienceLabApplication.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun saveLong(key: preferencesKey<Long>, value: Long) {
        ScienceLabApplication.dataStore.edit {
            it[key] = value
        }
    }
}