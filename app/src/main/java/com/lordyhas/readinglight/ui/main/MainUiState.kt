package com.lordyhas.readinglight.ui.main

import androidx.compose.ui.graphics.Color

enum class LightPreset(val title: String, val subtitle: String, val color: Color) {
    DEEP_RED(
        title = "Rouge Mélatonine",
        subtitle = "Zéro lumière bleue",
        color = Color(0xFFFF0000)
    ),
    WARM_AMBER(
        title = "Ambre Chaud",
        subtitle = "Lecture apaisante",
        color = Color(0xFFFFBF00)
    ),
    SOFT_WHITE(
        title = "Blanc Doux",
        subtitle = "Lumière naturelle",
        color = Color(0xFFFFF4E5)
    ),
    SEPIA(
        title = "Sépia",
        subtitle = "Papier ancien",
        color = Color(0xFF704214)
    )
}

data class MainUiState(
    val color: Color = Color(0xFFFFBF00),
    val brightness: Float = 0.70f,
    val effectiveBrightness: Float = 0.70f,
    val isOverlayVisible: Boolean = true,
    val selectedTimerMinutes: Int? = null,
    val remainingTimerSeconds: Long? = null,
    val isFadingOut: Boolean = false,
    val isCustomColorPickerVisible: Boolean = false
) {
    val formattedRemainingTime: String?
        get() = remainingTimerSeconds?.let { totalSecs ->
            val minutes = totalSecs / 60
            val seconds = totalSecs % 60
            String.format("%02d:%02d", minutes, seconds)
        }
}
