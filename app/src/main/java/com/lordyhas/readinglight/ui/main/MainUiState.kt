package com.lordyhas.readinglight.ui.main

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.lordyhas.readinglight.R

enum class LightPreset(
    @StringRes val title: Int,
    @StringRes val subtitle: Int,
    val color: Color
) {
    DEEP_RED(
        title = R.string.preset_deep_red,
        subtitle = R.string.preset_deep_red_subtitle,
        color = Color(0xFFFF0000)
    ),
    WARM_AMBER(
        title = R.string.preset_warm_amber,
        subtitle = R.string.preset_warm_amber_subtitle,
        color = Color(0xFFFFBF00)
    ),
    SOFT_WHITE(
        title = R.string.preset_soft_white,
        subtitle = R.string.preset_soft_white_subtitle,
        color = Color(0xFFFFF4E5)
    ),
    SEPIA(
        title = R.string.preset_sepia,
        subtitle = R.string.preset_sepia_subtitle,
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
