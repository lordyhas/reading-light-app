package com.lordyhas.readinglight

import androidx.compose.ui.graphics.Color
import com.lordyhas.readinglight.ui.main.LightPreset
import com.lordyhas.readinglight.ui.main.MainUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MainUiStateTest {

    @Test
    fun testFormattedRemainingTime() {
        val stateWithTime = MainUiState(
            remainingTimerSeconds = 90L
        )
        assertEquals("01:30", stateWithTime.formattedRemainingTime)

        val stateZero = MainUiState(
            remainingTimerSeconds = 0L
        )
        assertEquals("00:00", stateZero.formattedRemainingTime)

        val stateNull = MainUiState(
            remainingTimerSeconds = null
        )
        assertEquals(null, stateNull.formattedRemainingTime)
    }

    @Test
    fun testPresetsColors() {
        assertEquals(Color(0xFFFF0000), LightPreset.DEEP_RED.color)
        assertEquals(Color(0xFFFFBF00), LightPreset.WARM_AMBER.color)
        assertEquals(Color(0xFFFFF4E5), LightPreset.SOFT_WHITE.color)
        assertEquals(Color(0xFF704214), LightPreset.SEPIA.color)
    }
}
