package com.example.gid.ui.theme.main_screen.bottom_menu

import com.example.gid.R

sealed class BottomMenuItem(
    val title: String,
    val route: String,
    val iconId: Int

) {
    object Home : BottomMenuItem(
        title = "Home",
        route = "home",
        iconId = R.drawable.ic_home
    )
    object Map : BottomMenuItem(
        title = "Map",
        route = "map",
        iconId = R.drawable.ic_map
    )
    object Settings : BottomMenuItem(
        title = "Settings",
        route = "settings",
        iconId = R.drawable.ic_settings
    )
}
