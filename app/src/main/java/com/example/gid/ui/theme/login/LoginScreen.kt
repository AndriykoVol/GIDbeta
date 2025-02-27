package com.example.gid.ui.theme.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen() {
    val auth = FirebaseAuth.getInstance()

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Log.d("MyLog", "Current user: ${auth.currentUser?.email}")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            signIn(auth, emailState.value, passwordState.value)
        }) {
            Text(text = "Sign In")
        }
        Button(onClick = {
            signUp(auth, emailState.value, passwordState.value)
        }) {
            Text(text = "Sign Up")
        }
        Button(onClick = {
            signOut(auth)
        }) {
            Text(text = "Sign Out")
        }
    }
}



private fun signUp(auth: FirebaseAuth, email: String, password: String) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("MyLog", "Sign up successfully: ${auth.currentUser?.email}")
            } else {
                Log.d("MyLog", "Sign up failed: ${task.exception?.message}")
            }
        }
}

private fun signIn(auth: FirebaseAuth, email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("MyLog", "Sign in successfully: ${auth.currentUser?.email}")
            } else {
                Log.d("MyLog", "Sign in failed: ${task.exception?.message}")
            }
        }
}

private fun signOut(auth: FirebaseAuth) {
    auth.signOut()
}

