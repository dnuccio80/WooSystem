package org.example.minisystem.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class MonthlyScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Box(Modifier.fillMaxSize().background(color = Color.Black)) {
            Text("MENSUAL", color = Color.White)
        }
    }
}