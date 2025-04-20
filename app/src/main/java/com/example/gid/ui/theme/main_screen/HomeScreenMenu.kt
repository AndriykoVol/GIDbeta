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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMenu() {
    // Minimalist dark blue and white color scheme
    val darkBlue = Color(0xFF0A2342)
    val midBlue = Color(0xFF173B66)
    val lightBlue = Color(0xFF2C5282)
    val accentBlue = Color(0xFF4299E1)
    val pureWhite = Color(0xFFFFFFFF)
    val softWhite = Color(0xFFF0F4F8)
    val darkBackground = Color(0xFF091428)
    val cardBackground = Color(0xFF102040)

    var showProfileDetails by remember { mutableStateOf(true) }
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var profileName by remember { mutableStateOf("Андрій Волинець") }
    var profileDescription by remember { mutableStateOf("Досвідчений турист") }

    Surface(modifier = Modifier.fillMaxSize(), color = darkBackground) {
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
                        color = pureWhite
                    )
                )
                IconButton(onClick = { showProfileDetails = !showProfileDetails }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle profile",
                        tint = pureWhite
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
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
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
                                    colors = listOf(midBlue, darkBlue)
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(contentAlignment = Alignment.BottomEnd) {
                                Image(
                                    painter = painterResource(id = R.drawable.tourist),
                                    contentDescription = "Фото профілю",
                                    modifier = Modifier
                                        .size(90.dp)
                                        .clip(CircleShape)
                                        .border(width = 2.dp, color = pureWhite, shape = CircleShape)
                                )

                                IconButton(
                                    onClick = { showEditProfileDialog = true },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(accentBlue, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = "Edit Profile",
                                        tint = pureWhite,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = profileName,
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = pureWhite)
                            )
                            Text(
                                text = profileDescription,
                                style = TextStyle(fontSize = 16.sp, color = softWhite.copy(alpha = 0.9f))
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
                            Triple("75", "Друзів", Icons.Default.Person),
                            Triple("234", "Вподобання", Icons.Default.Favorite),
                            Triple("141", "Місць", Icons.Default.LocationOn)
                        )
                        stats.forEach { (number, label, icon) ->
                            StatisticItem(number = number, label = label, icon = icon, primaryColor = accentBlue, textColor = pureWhite)
                        }
                    }
                }
            }

            // Featured Places Section
            SectionHeader(
                title = "Рекомендовані місця",
                subtitle = "Топові місця для відвідування",
                primaryColor = accentBlue,
                textColor = pureWhite
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
                        primaryColor = midBlue,
                        accentColor = accentBlue,
                        backgroundColor = cardBackground,
                        textColor = pureWhite
                    )
                }
            }

            // Nearby Places Section
            SectionHeader(
                title = "Місця поблизу",
                subtitle = "Дослідіть свій район",
                primaryColor = accentBlue,
                textColor = pureWhite
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
                        primaryColor = midBlue,
                        accentColor = accentBlue,
                        backgroundColor = cardBackground,
                        textColor = pureWhite
                    )
                }
            }
        }
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        var nameInput by remember { mutableStateOf(profileName) }
        var descriptionInput by remember { mutableStateOf(profileDescription) }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Редагувати профіль", color = darkBlue) },
            containerColor = softWhite,
            titleContentColor = darkBlue,
            text = {
                Column {
                    TextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Ім'я") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = midBlue,
                            containerColor = softWhite
                        )
                    )

                    TextField(
                        value = descriptionInput,
                        onValueChange = { descriptionInput = it },
                        label = { Text("Опис") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = midBlue,
                            containerColor = softWhite
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        profileName = nameInput
                        profileDescription = descriptionInput
                        showEditProfileDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = midBlue)
                ) {
                    Text("Зберегти")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("Скасувати", color = midBlue)
                }
            }
        )
    }
}

@Composable
fun StatisticItem(number: String, label: String, icon: ImageVector, primaryColor: Color, textColor: Color) {
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
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
        )
        Text(
            text = label,
            style = TextStyle(fontSize = 14.sp, color = textColor.copy(alpha = 0.7f))
        )
    }
}

@Composable
fun SectionHeader(title: String, subtitle: String, primaryColor: Color, textColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textColor),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = subtitle,
            style = TextStyle(fontSize = 14.sp, color = primaryColor),
            modifier = Modifier.fillMaxWidth()
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
fun FeaturedPlaceCard(
    place: Place,
    primaryColor: Color,
    accentColor: Color,
    backgroundColor: Color,
    textColor: Color
) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(end = 16.dp, bottom = 8.dp)
            .width(220.dp)
            .height(240.dp)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
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
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.White,
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
                        style = TextStyle(fontSize = 12.sp, color = textColor)
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
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor),
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
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.location,
                        style = TextStyle(fontSize = 14.sp, color = textColor.copy(alpha = 0.7f))
                    )
                }

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor
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
fun NearbyPlaceCard(
    place: Place,
    primaryColor: Color,
    accentColor: Color,
    backgroundColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
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
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textColor),
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
                        tint = accentColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = place.location,
                        style = TextStyle(fontSize = 12.sp, color = textColor.copy(alpha = 0.7f))
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = place.rating.toString(),
                        style = TextStyle(fontSize = 12.sp, color = textColor.copy(alpha = 0.7f))
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Детальніше →",
                    style = TextStyle(fontSize = 12.sp, color = accentColor, fontWeight = FontWeight.Medium),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}