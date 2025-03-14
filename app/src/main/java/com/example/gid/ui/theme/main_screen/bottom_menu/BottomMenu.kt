package com.example.gid.ui.theme.main_screen.bottom_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gid.R



@Composable
fun BottomMenu(navController: NavController) {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Map,
        BottomMenuItem.Settings
    )
    val selectedItem = remember { mutableStateOf("home") }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.value == item.route,
                onClick = {
                    selectedItem.value = item.route
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(painter = painterResource(id =  item.iconId),
                        contentDescription = null  )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
 }

