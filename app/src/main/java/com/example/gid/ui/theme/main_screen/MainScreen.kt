package com.example.gid.ui.theme.main_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth


@Preview(showBackground = true)
@Composable

fun MainScreen() {
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(0.7f),
        drawerContent = {
            DrawerHeader()
        }
    ) {

    }
}