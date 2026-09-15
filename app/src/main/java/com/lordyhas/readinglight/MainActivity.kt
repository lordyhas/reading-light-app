package com.lordyhas.readinglight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lordyhas.readinglight.data.UserPreferencesRepository
import com.lordyhas.readinglight.ui.main.MainScreen
import com.lordyhas.readinglight.ui.main.MainViewModel
import com.lordyhas.readinglight.ui.theme.ReadingLightTheme

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        UserPreferencesRepository(applicationContext)
    }

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReadingLightTheme {
                MainScreen(
                    viewModel = viewModel,
                    onCloseApp = { finish() }
                )
            }
        }
    }
}