package com.example.gid.ui.theme.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gid.R
import com.google.firebase.auth.FirebaseAuth
import com.example.gid.ui.theme.login.data.MainScreenDataObject
import com.example.gid.ui.theme.login.data.LoginScreenObject


@Composable
fun SignInScreen(
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val auth = remember { FirebaseAuth.getInstance() }

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorState = remember { mutableStateOf("") }

    // Define colors
    val backgroundColor = Color(0xFF1A5336)
    val buttonColor = Color(0xFF8AB544)
    val linkColor = Color(0xFFACD95A)

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.greenback),
            contentDescription = "Forest Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Content Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                text = "Вхід",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 50.dp)
            )

            // Email Field
            RoundedCornerTextField(
                text = emailState.value,
                label = "Електронна пошта",
                onValueChange = { emailState.value = it },
                isPassword = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password Field
            RoundedCornerTextField(
                text = passwordState.value,
                label = "Пароль",
                onValueChange = { passwordState.value = it },
                isPassword = true
            )

            // Error message if any
            if (errorState.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorState.value,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Login Button
            Button(
                onClick = {
                    signIn(
                        auth = auth,
                        email = emailState.value,
                        password = passwordState.value,
                        onSignInSuccess = { navData ->
                            onNavigateToMainScreen(navData)
                        },
                        onSignInFailed = { error ->
                            errorState.value = error
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Увійти",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Don't have an account text
            Text(
                text = "Немає акаунту?",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Register link
            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = "Зареєструватися",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = linkColor
                )
            }
        }
    }
}

// Reuse the sign in function from your original code
fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (MainScreenDataObject) -> Unit,
    onSignInFailed: (String) -> Unit
) {
    if (email.isEmpty() || password.isEmpty()) {
        onSignInFailed("Заповніть всі поля")
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignInSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener { exception ->
            onSignInFailed(exception.message ?: "Помилка входу")
        }
}