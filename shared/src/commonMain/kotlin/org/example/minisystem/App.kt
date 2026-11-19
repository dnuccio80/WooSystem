package org.example.minisystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition

import org.example.minisystem.di.dataModule
import org.example.minisystem.di.domainModule
import org.example.minisystem.di.platformModule
import org.example.minisystem.di.uiModule
import org.example.minisystem.ui.DailyScreen
import org.example.minisystem.ui.MenuItemData
import org.example.minisystem.ui.MonthlyScreen
import org.example.minisystem.ui.products.ProductsScreen
import org.example.minisystem.ui.Routes
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinApplication(application = {
            modules(
                dataModule, domainModule, uiModule,
                platformModule()
            )
        }) {

            val menuItemList = listOf(
                MenuItemData(
                    title = "Caja diaria",
                    route = Routes.Daily,
                    icon = Icons.Default.AttachMoney
                ),
                MenuItemData(
                    title = "Mensual",
                    route = Routes.Monthly,
                    icon = Icons.Default.CalendarMonth
                ),
                MenuItemData(
                    title = "Productos",
                    route = Routes.Products,
                    icon = Icons.Default.Inventory2
                ),

                )

            var menuItemSelected by remember { mutableStateOf(Routes.Daily.route) }

            Navigator(screen = DailyScreen()) { navigator ->

                val currentScreen = navigator.lastItem

                Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
                    Column(
                        modifier = Modifier
                            .safeContentPadding()
                            .fillMaxSize(),
                    ) {
                        MainHeader()
                        Scaffold { paddingValues ->
                            Row(Modifier.fillMaxSize()) {
                                SideBar(
                                    menuItemList,
                                    menuItemSelected
                                ) { newMenuItemSelected ->
                                    menuItemSelected = newMenuItemSelected
                                    when (menuItemSelected) {
                                        Routes.Daily.route -> if (currentScreen !is DailyScreen) {
                                            navigator.popUntilRoot()
                                            navigator.replace(DailyScreen())
                                        }

                                        Routes.Monthly.route -> if (currentScreen !is MonthlyScreen) {
                                            navigator.popUntilRoot()
                                            navigator.replace(MonthlyScreen())
                                        }

                                        Routes.Products.route -> if (currentScreen !is ProductsScreen) {
                                            navigator.popUntilRoot()
                                            navigator.replace(ProductsScreen())
                                        }
                                    }
                                }
                                FadeTransition(navigator)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SideBar(menuItemList: List<MenuItemData>, menuItemSelected: String, onClick: (String) -> Unit) {
    Box(modifier = Modifier.background(Color.DarkGray)) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            menuItemList.forEach { item ->
                MenuItem(
                    item, menuItemSelected,
                ) { newMenuItem ->
                    onClick(newMenuItem)
                }
            }
        }
    }
}

@Composable
private fun MenuItem(
    itemData: MenuItemData,
    menuItemSelected: String,
    onClick: (String) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier =
            Modifier
                .background(
                    if (menuItemSelected == itemData.route.route) Color.LightGray.copy(
                        alpha = .3f
                    ) else if (isHovered) Color.DarkGray.copy(alpha = .8f) else Color.Transparent
                )
                .hoverable(interactionSource)
                .clickable { onClick(itemData.route.route) }
                .pointerHoverIcon(PointerIcon.Hand)

    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(itemData.icon, contentDescription = "menu item", tint = Color.White)
            Text(itemData.title, color = Color.White, fontSize = 14.sp)
        }
    }
}
