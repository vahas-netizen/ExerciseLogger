package com.example.consciousworkouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.consciousworkouts.presentation.navigation.Navigation
import com.example.consciousworkouts.presentation.ui.theme.ConsciousWorkoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            ConsciousWorkoutsTheme {
                Navigation()
            }
        }
    }
}



