package com.lordyhas.readinglight.util

import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object WindowUtils {

    /**
     * Toggles or sets immersive fullscreen mode (hiding or showing status & navigation bars).
     */
    fun setImmersiveMode(window: Window, isImmersive: Boolean) {
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        if (isImmersive) {
            insetsController.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            insetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    /**
     * Sets the brightness of the current window only without modifying system-wide settings.
     * @param brightness value between 0.01f and 1.0f
     */
    fun setScreenBrightness(window: Window, brightness: Float) {
        val layoutParams = window.attributes
        layoutParams.screenBrightness = brightness.coerceIn(0.01f, 1.0f)
        window.attributes = layoutParams
    }

    /**
     * Keeps the screen on to prevent sleep mode while reading.
     */
    fun setKeepScreenOn(window: Window, keepOn: Boolean) {
        if (keepOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}
