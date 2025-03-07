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

@Composable
fun LoginScreen() {

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
            label = "Email",
            onValueChange = { emailState.value = it }
        )
        Spacer(modifier = Modifier.height(15.dp))

        RoundedCornerTextField(
            text = passwordState.value,
            label = "Password",
            onValueChange = { passwordState.value = it }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {

        }) {
            Text(text = "Sign In")
        }
        Button(onClick = {

        }) {
            Text(text = "Sign Up")
        }
    }
}