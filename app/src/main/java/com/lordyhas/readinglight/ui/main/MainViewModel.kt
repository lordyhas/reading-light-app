package com.lordyhas.readinglight.ui.main

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lordyhas.readinglight.data.UserPreferencesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface UiEvent {
    object CloseApp : UiEvent
}

class MainViewModel(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private var autoHideJob: Job? = null
    private var sleepTimerJob: Job? = null

    init {
        // Collect saved preferences
        viewModelScope.launch {
            preferencesRepository.userPreferencesFlow.collect { prefs ->
                _uiState.update { current ->
                    current.copy(
                        color = Color(prefs.colorArgb),
                        brightness = prefs.brightness,
                        effectiveBrightness = if (current.isFadingOut) current.effectiveBrightness else prefs.brightness
                    )
                }
            }
        }
        // Start initial auto-hide countdown for the overlay
        startAutoHideTimer()
    }

    /**
     * Toggles overlay visibility when user taps the screen.
     */
    fun toggleOverlay() {
        val newVisibility = !_uiState.value.isOverlayVisible
        _uiState.update { it.copy(isOverlayVisible = newVisibility) }
        if (newVisibility) {
            startAutoHideTimer()
        } else {
            autoHideJob?.cancel()
        }
    }

    /**
     * Resets the 4-second auto-hide timer whenever the user interacts with controls.
     */
    fun resetInactivityTimer() {
        if (_uiState.value.isOverlayVisible) {
            startAutoHideTimer()
        }
    }

    private fun startAutoHideTimer() {
        autoHideJob?.cancel()
        autoHideJob = viewModelScope.launch {
            delay(4000L)
            _uiState.update { it.copy(isOverlayVisible = false) }
        }
    }

    fun onBrightnessChange(newBrightness: Float) {
        val clamped = newBrightness.coerceIn(0.01f, 1.0f)
        _uiState.update { current ->
            current.copy(
                brightness = clamped,
                effectiveBrightness = clamped,
                isFadingOut = false
            )
        }
        resetInactivityTimer()
        viewModelScope.launch {
            preferencesRepository.saveBrightness(clamped)
        }
    }

    fun onColorChange(newColor: Color) {
        _uiState.update { it.copy(color = newColor) }
        resetInactivityTimer()
        viewModelScope.launch {
            preferencesRepository.saveColor(newColor.toArgb())
        }
    }

    fun toggleCustomColorPicker() {
        _uiState.update { it.copy(isCustomColorPickerVisible = !it.isCustomColorPickerVisible) }
        resetInactivityTimer()
    }

    /**
     * Starts or stops the sleep timer.
     * @param minutes number of minutes for the timer, or null to disable
     */
    fun setSleepTimer(minutes: Int?) {
        sleepTimerJob?.cancel()
        resetInactivityTimer()

        if (minutes == null) {
            _uiState.update { current ->
                current.copy(
                    selectedTimerMinutes = null,
                    remainingTimerSeconds = null,
                    isFadingOut = false,
                    effectiveBrightness = current.brightness
                )
            }
            return
        }

        val totalSeconds = minutes * 60L
        _uiState.update { current ->
            current.copy(
                selectedTimerMinutes = minutes,
                remainingTimerSeconds = totalSeconds,
                isFadingOut = false,
                effectiveBrightness = current.brightness
            )
        }

        sleepTimerJob = viewModelScope.launch {
            var secondsLeft = totalSeconds
            val initialBrightness = _uiState.value.brightness
            val fadeOutDurationSeconds = 60L

            while (secondsLeft > 0) {
                delay(1000L)
                secondsLeft--

                if (secondsLeft <= fadeOutDurationSeconds) {
                    // Smooth progressive dimming over 60 seconds down to 0.01f
                    val progress = (secondsLeft.toFloat() / fadeOutDurationSeconds).coerceIn(0f, 1f)
                    val dimmedBrightness = (initialBrightness * progress).coerceAtLeast(0.01f)
                    _uiState.update { current ->
                        current.copy(
                            remainingTimerSeconds = secondsLeft,
                            isFadingOut = true,
                            effectiveBrightness = dimmedBrightness
                        )
                    }
                } else {
                    _uiState.update { current ->
                        current.copy(
                            remainingTimerSeconds = secondsLeft,
                            isFadingOut = false
                        )
                    }
                }
            }

            // Timer finished: send close event to activity
            _uiState.update { current ->
                current.copy(
                    remainingTimerSeconds = 0,
                    effectiveBrightness = 0.01f
                )
            }
            _events.send(UiEvent.CloseApp)
        }
    }

    override fun onCleared() {
        super.onCleared()
        autoHideJob?.cancel()
        sleepTimerJob?.cancel()
    }
}
