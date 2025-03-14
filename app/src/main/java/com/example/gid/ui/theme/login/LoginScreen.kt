package com.example.gid.ui.theme.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.gid.R
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen() {
    val auth = remember { FirebaseAuth.getInstance() }

    val errorState = remember { mutableStateOf("") }

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo"
        )

        Spacer(modifier = Modifier.height(15.dp))

        RoundedCornerTextField(
            text = emailState.value,
            label = "Пошта",
            onValueChange = { emailState.value = it }
        )
        Spacer(modifier = Modifier.height(15.dp))

        RoundedCornerTextField(
            text = passwordState.value,
            label = "Пароль(мін. 9 символів)",
            onValueChange = { passwordState.value = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
                fontSize = 20.sp
            )
        }
        Button(onClick = {
            signIn(
                auth = auth,
                email = emailState.value,
                password = passwordState.value,
                onSignInSuccess = {
                    Log.d("MyLog", "Потужно")
                },
                onSignInFailed = { error ->
                    errorState.value = error
                }
            )
        }) {
            LoginButton(text = "Увійти")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            signUp(
                auth = auth,
                email = emailState.value,
                password = passwordState.value,
                onSignUpSuccess = {
                    Log.d("MyLog", "Потужно")
                },
                onSignUpFailed = { error ->
                    errorState.value = error
                }
            )
        }) {
            LoginButton(text = "Зареєструватися")
        }
    }
}

@Composable
fun LoginButton(text: String) {
    Text(text = text)
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSuccess: () -> Unit,
    onSignUpFailed: (String) -> Unit
) {
    if (email.isEmpty() || password.isEmpty()) {
        onSignUpFailed("СОСАВ?")
        return
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignUpSuccess()
            } else {
                onSignUpFailed(task.exception?.message ?: "Помилка реєстрації")
            }
        }
        .addOnFailureListener { exception ->
            onSignUpFailed(exception.message ?: "Помилка реєстрації")
        }
}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: () -> Unit,
    onSignInFailed: (String) -> Unit
) {
    if (email.isEmpty() || password.isEmpty()) {
        onSignInFailed("Заповніть всі поля")
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignInSuccess()
            } else {
                onSignInFailed(task.exception?.message ?: "Помилка входу")
            }
        }
        .addOnFailureListener { exception ->
            onSignInFailed(exception.message ?: "Помилка входу")
        }
}