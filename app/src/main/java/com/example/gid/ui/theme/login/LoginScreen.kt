package com.example.gid.ui.theme.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gid.R
import com.example.gid.ui.theme.login.data.LoginScreenObject
import com.google.firebase.auth.FirebaseAuth
import com.example.gid.ui.theme.login.data.MainScreenDataObject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedCornerTextField(
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    val darkGreen = Color(0xFF0A3B20)

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.White.copy(alpha = 0.8f)) },
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(65.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(darkGreen),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
            cursorColor = Color.White
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun LoginScreen(
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit,
    onNavigateToSignIn: () -> Unit
    ) {
    val auth = remember { FirebaseAuth.getInstance() }

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }
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
                text = "Реєстрація",
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

            Spacer(modifier = Modifier.height(20.dp))

            // Confirm Password Field
            RoundedCornerTextField(
                text = confirmPasswordState.value,
                label = "Підтвердьте пароль",
                onValueChange = { confirmPasswordState.value = it },
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

            // Register Button
            Button(
                onClick = {
                    if (passwordState.value != confirmPasswordState.value) {
                        errorState.value = "Паролі не співпадають"
                        return@Button
                    }

                    signUp(
                        auth = auth,
                        email = emailState.value,
                        password = passwordState.value,
                        onSignUpSuccess = { navData ->
                            onNavigateToMainScreen(navData)
                        },
                        onSignUpFailed = { error ->
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
                    text = "Зареєструватися",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Already have an account text
            Text(
                text = "Вже є акаунт?",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onNavigateToSignIn) {
                Text(
                    text = "Увійти",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = linkColor
                )
            }
        }
    }
}

// Reuse the sign up function from your original code
fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSuccess: (MainScreenDataObject) -> Unit,
    onSignUpFailed: (String) -> Unit
) {
    if (email.isEmpty() || password.isEmpty()) {
        onSignUpFailed("Заповніть всі поля")
        return
    }

    if (password.length < 9) {
        onSignUpFailed("Пароль має бути не менше 9 символів")
        return
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignUpSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener { exception ->
            onSignUpFailed(exception.message ?: "Помилка реєстрації")
        }
}