package com.example.gid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gid.ui.theme.main_screen.MainScreen
import com.example.gid.ui.theme.login.LoginScreen
import com.example.gid.ui.theme.main_screen.SettingsScreen
import com.example.gid.ui.theme.main_screen.MapScreen
import com.example.gid.ui.theme.login.data.LoginScreenObject
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController,
                startDestination = LoginScreenObject) {

                composable<LoginScreenObject> {
                    LoginScreen()
                }
            }
        }
    }
}

