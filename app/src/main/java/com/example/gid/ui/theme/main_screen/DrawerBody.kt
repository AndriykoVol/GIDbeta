package com.example.gid.ui.theme.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gid.R

@Composable
fun DrawerBody() {
    val darkBlue =  Color(0xFF1B3A2F)
    val green = Color(0xFF88B04B)
    val white = Color(0xFFFFFFFF)
    val dividerColor = Color(0xFF4F7751)

    val categoriesList = listOf(
        "Мої відгуки",
        "Обговорення",
        "Збережене",
        "Підтримка"
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(darkBlue)
    ) {
        Image(
            painter = painterResource(id = R.drawable.greenback),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.5f,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(categoriesList) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* handle click */ }
                    ) {
                        Text(
                            text = item,
                            color = white,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(dividerColor)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* handle save action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = green,
                    contentColor = darkBlue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Зберегти", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = white)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
