package com.example.gid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gid.ui.theme.login.SignInScreen
import com.example.gid.ui.theme.login.LoginScreen
import com.example.gid.ui.theme.login.data.MainScreenDataObject
import com.example.gid.ui.theme.main_screen.MainScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("WrongStartDestinationType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_screen"
    ) {
        composable("login_screen") {
            LoginScreen(
                onNavigateToMainScreen = { navData ->
                    navController.navigate("main_screen/${navData.uid}")
                },
                onNavigateToSignIn = {
                    navController.navigate("sign_in_screen")
                }
            )
        }

        composable("sign_in_screen") {
            SignInScreen(
                onNavigateToMainScreen = { navData ->
                    navController.navigate("main_screen/${navData.uid}") {
                        popUpTo("login_screen") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login_screen") {
                        popUpTo("sign_in_screen") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "main_screen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            MainScreen()
        }
    }
}
