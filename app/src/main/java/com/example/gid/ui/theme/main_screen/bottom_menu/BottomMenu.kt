package com.example.gid.ui.theme.main_screen.bottom_menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.gid.R

@Composable
fun BottomMenu(navController: NavController) {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Map,
        BottomMenuItem.Settings
    )

    val currentDestination = navController.currentDestination

    NavigationBar(
        containerColor = Color(0xFF002510),
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo("home") {
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null,
                        tint = if (isSelected) Color.Yellow else Color.White
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(top = 4.dp),
                        color = if (isSelected) Color.Yellow else Color.White
                    )
                }
            )
        }
    }
}
