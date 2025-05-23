package ru.mareanexx.jotterblogapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.mareanexx.common.ui.theme.JotterBlogAppTheme
import ru.mareanexx.common.utils.SettingsManager
import ru.mareanexx.common.utils.UserSessionManager
import ru.mareanexx.jotterblogapp.presentation.navigation.AppNavGraph
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var userSessionManager: UserSessionManager

    @Inject lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isLightTheme by settingsManager.themeSettings.collectAsState(initial = true)

            JotterBlogAppTheme(darkTheme = !isLightTheme) {
                val navHostController = rememberNavController()
                AppNavGraph(navHostController)
            }
        }
    }
}