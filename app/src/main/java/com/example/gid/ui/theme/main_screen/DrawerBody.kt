package com.example.gid.ui.theme.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.gid.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.gid.ui.theme.Grey


@Composable
fun DrawerBody() {
    val categoriesList = listOf(
        "Мої відгуки",
        "Обговорення",
        "Збережене",
        "Підтримка"
    )
    Box(modifier = Modifier.fillMaxSize().background(Color.Gray)) {
       Image(
           painter = painterResource(id = R.drawable.background),
           contentDescription = "Background",
           modifier = Modifier.fillMaxSize(),
           alpha = 0.5f,
           contentScale = ContentScale.Crop
       )
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(16.dp))

        }
        LazyColumn(Modifier.fillMaxSize()) {
            items(categoriesList) { item ->
                Column(Modifier.fillMaxWidth()
                    .clickable {  }
                ) {
                    Text(
                        text = item,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .wrapContentWidth()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Grey)
                    )
                }
            }
        }
    }
}