package com.example.gid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gid.ui.theme.login.LoginScreen
import com.example.gid.ui.theme.login.data.LoginScreenObject
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gid.ui.theme.main_screen.MainScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("WrongStartDestinationType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "login_screen"
            ) {
                composable("login_screen") {
                    LoginScreen { navData ->
                        navController.navigate("main_screen/$navData")
                    }
                }
                composable(
                    route = "main_screen/{navData}",
                    arguments = listOf(navArgument("navData") { type = NavType.StringType })
                ) { backStackEntry ->
                    MainScreen()
                }
            }
        }
    }
}