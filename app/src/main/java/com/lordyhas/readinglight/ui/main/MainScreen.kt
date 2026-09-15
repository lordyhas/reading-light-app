package com.lordyhas.readinglight.ui.main

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lordyhas.readinglight.ui.components.ControlPanelOverlay
import com.lordyhas.readinglight.util.WindowUtils

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onCloseApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val window = (context as? Activity)?.window

    // Listen for close app / one-off events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.CloseApp -> onCloseApp()
            }
        }
    }

    // Keep screen on while the app is running
    DisposableEffect(window) {
        window?.let { WindowUtils.setKeepScreenOn(it, true) }
        onDispose {
            window?.let { WindowUtils.setKeepScreenOn(it, false) }
        }
    }

    // Dynamically update window brightness (using effective brightness)
    LaunchedEffect(uiState.effectiveBrightness, window) {
        window?.let {
            WindowUtils.setScreenBrightness(it, uiState.effectiveBrightness)
        }
    }

    // Hide/show system bars based on overlay visibility
    LaunchedEffect(uiState.isOverlayVisible, window) {
        window?.let {
            WindowUtils.setImmersiveMode(it, isImmersive = !uiState.isOverlayVisible)
        }
    }

    // Full screen background light surface
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(uiState.color)
            .pointerInput(Unit) {
                detectTapGestures {
                    viewModel.toggleOverlay()
                }
            }
    ) {
        // Floating control overlay
        AnimatedVisibility(
            visible = uiState.isOverlayVisible,
            enter = fadeIn(animationSpec = tween(300)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(300)
            ),
            exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
                targetOffsetY = { it / 2 },
                animationSpec = tween(300)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            ControlPanelOverlay(
                uiState = uiState,
                onBrightnessChange = viewModel::onBrightnessChange,
                onColorChange = viewModel::onColorChange,
                onToggleCustomPicker = viewModel::toggleCustomColorPicker,
                onSetSleepTimer = viewModel::setSleepTimer,
                onUserInteraction = viewModel::resetInactivityTimer
            )
        }
    }
}
