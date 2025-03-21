package com.example.gid.ui.theme.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMenu() {
    // Custom color scheme
    val primaryBlue = Color(0xFF1565C0)
    val secondaryOrange = Color(0xFFFF8F00)
    val backgroundColor = Color(0xFFF5F7FA)
    val cardBackground = Color.White

    var showProfileDetails by remember { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize(), color = backgroundColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "TravelBuddy",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryBlue
                    )
                )
                IconButton(onClick = { showProfileDetails = !showProfileDetails }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Toggle profile",
                        tint = secondaryOrange
                    )
                }
            }

            // Profile Card with animation
            AnimatedVisibility(
                visible = showProfileDetails,
                enter = fadeIn(animationSpec = tween(500)) +
                        slideInVertically(initialOffsetY = { -40 }, animationSpec = tween(500))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = cardBackground
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(primaryBlue, primaryBlue.copy(alpha = 0.7f))
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.tourist),
                                contentDescription = "Фото профілю",
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Андрій Волинець",
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            )
                            Text(
                                text = "Досвідчений турист",
                                style = TextStyle(fontSize = 16.sp, color = Color.White.copy(alpha = 0.9f))
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        val stats = listOf(
                            Triple("75", "Друзів", Icons.Default.Star),
                            Triple("234", "Вподобання", Icons.Default.Favorite),
                            Triple("141", "Місць", Icons.Default.LocationOn)
                        )
                        stats.forEach { (number, label, icon) ->
                            StatisticItem(number = number, label = label, icon = icon, primaryColor = primaryBlue)
                        }
                    }
                }
            }

            // Featured Places Section
            SectionHeader(
                title = "Рекомендовані місця",
                subtitle = "Топові місця для відвідування",
                primaryColor = primaryBlue
            )

            val featuredPlaces = listOf(
                Place("Козятинський вокзал", R.drawable.kozyatun, "Україна", 4.7f),
                Place("Великий китайський мур", R.drawable.china, "Китай", 4.9f),
                Place("Костел св. Анни", R.drawable.bar, "Україна", 4.5f),
                Place("Голлівуд", R.drawable.holly, "США", 4.8f)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(featuredPlaces) { place ->
                    FeaturedPlaceCard(
                        place = place,
                        primaryColor = primaryBlue,
                        secondaryColor = secondaryOrange
                    )
                }
            }

            // Nearby Places Section
            SectionHeader(
                title = "Місця поблизу",
                subtitle = "Дослідіть свій район",
                primaryColor = primaryBlue
            )

            val nearbyPlaces = listOf(
                Place("Парк Шевченка", R.drawable.kozyatun, "2 км", 4.3f),
                Place("Музей мистецтв", R.drawable.bar, "3.5 км", 4.6f),
                Place("Оперний театр", R.drawable.holly, "5 км", 4.8f),
                Place("Ботанічний сад", R.drawable.china, "7.2 км", 4.2f)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = Modifier.height(280.dp)
            ) {
                items(nearbyPlaces) { place ->
                    NearbyPlaceCard(
                        place = place,
                        primaryColor = primaryBlue,
                        secondaryColor = secondaryOrange
                    )
                }
            }
        }
    }
}

@Composable
fun StatisticItem(number: String, label: String, icon: ImageVector, primaryColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = number,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        )
        Text(
            text = label,
            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
        )
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String, primaryColor: Color) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        )
        Text(
            text = subtitle,
            style = TextStyle(fontSize = 14.sp, color = primaryColor)
        )
    }
}

data class Place(
    val name: String,
    val imageRes: Int,
    val location: String,
    val rating: Float
)

@Composable
fun FeaturedPlaceCard(place: Place, primaryColor: Color, secondaryColor: Color) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(end = 16.dp, bottom = 8.dp)
            .width(220.dp)
            .height(240.dp)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = place.imageRes),
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) secondaryColor else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(primaryColor.copy(alpha = 0.7f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.rating.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.White)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = place.name,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.location,
                        style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                    )
                }

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    ),
                    contentPadding = PaddingValues(vertical = 6.dp)
                ) {
                    Text("Переглянути")
                }
            }
        }
    }
}

@Composable
fun NearbyPlaceCard(place: Place, primaryColor: Color, secondaryColor: Color) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row {
            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = place.name,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = place.location,
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = secondaryColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = place.rating.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Детальніше →",
                    style = TextStyle(fontSize = 12.sp, color = primaryColor, fontWeight = FontWeight.Medium),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}