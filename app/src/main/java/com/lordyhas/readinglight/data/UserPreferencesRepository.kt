package com.lordyhas.readinglight.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reading_light_preferences")

data class UserPreferences(
    val colorArgb: Int,
    val brightness: Float
)

class UserPreferencesRepository(private val context: Context) {

    companion object {
        private val KEY_COLOR = intPreferencesKey("selected_color_argb")
        private val KEY_BRIGHTNESS = floatPreferencesKey("selected_brightness")

        // Default: Warm Amber (#FFBF00) and 70% brightness
        const val DEFAULT_COLOR_ARGB: Int = 0xFFFFBF00.toInt()
        const val DEFAULT_BRIGHTNESS: Float = 0.70f
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val color = preferences[KEY_COLOR] ?: DEFAULT_COLOR_ARGB
            val brightness = preferences[KEY_BRIGHTNESS] ?: DEFAULT_BRIGHTNESS
            UserPreferences(colorArgb = color, brightness = brightness)
        }

    suspend fun saveColor(colorArgb: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_COLOR] = colorArgb
        }
    }

    suspend fun saveBrightness(brightness: Float) {
        context.dataStore.edit { preferences ->
            preferences[KEY_BRIGHTNESS] = brightness.coerceIn(0.01f, 1.0f)
        }
    }
}
