package com.example.gid.ui.theme.main_screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.gid.ui.theme.Grey
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.gid.R

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(Grey),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}