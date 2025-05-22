package ru.mareanexx.jotterblogapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.mareanexx.common.ui.theme.JotterBlogAppTheme
import ru.mareanexx.jotterblogapp.presentation.navigation.AppNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ru.mareanexx.common.ui.theme.JotterBlogAppTheme {
                val navHostController = rememberNavController()
                AppNavGraph(navHostController)
            }
        }
    }
}