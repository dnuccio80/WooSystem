package org.example.minisystem.ui

sealed class Routes(val route:String) {
    data object Daily: Routes("daily")
    data object Monthly: Routes("monthly")
    data object Products: Routes("products")
}
