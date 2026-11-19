package org.example.minisystem.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.minisystem.MainContainer

class DailyScreen: Screen {

    @Composable
    override fun Content() {

        MainContainer(
            titleText = "Ventas diarias",
            buttonText = "Nueva venta",
            onButtonClick = { },
            content = {
                
            }
        )
    }
}