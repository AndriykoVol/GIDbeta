package com.example.gid.ui.theme.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch


data class PlaceItem(
    val id: String,
    val name: String,
    val address: String,
    val distance: String,
    val position: LatLng,
    val category: String = "Інше" // Adding category for filter functionality
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Define color scheme
    val darkBlue = Color(0xFF0A2342)
    val midBlue = Color(0xFF173B66)
    val lightBlue = Color(0xFF2C5282)
    val accentBlue = Color(0xFF4299E1)
    val pureWhite = Color(0xFFFFFFFF)
    val softWhite = Color(0xFFF0F4F8)
    val darkBackground = Color(0xFF091428)
    val cardBackground = Color(0xFF102040)

    // State variables
    var searchQuery by remember { mutableStateOf("") }
    var mapProperties by remember { mutableStateOf(
        MapProperties(
            isMyLocationEnabled = false,
            mapStyleOptions = MapStyleOptions("""
                [
                  {
                    "featureType": "all",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#0a2342" }
                    ]
                  },
                  {
                    "featureType": "all",
                    "elementType": "labels.text.fill",
                    "stylers": [
                      { "color": "#ffffff" }
                    ]
                  },
                  {
                    "featureType": "all",
                    "elementType": "labels.text.stroke",
                    "stylers": [
                      { "color": "#173b66" }
                    ]
                  },
                  {
                    "featureType": "road",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#173b66" }
                    ]
                  },
                  {
                    "featureType": "water",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#091428" }
                    ]
                  }
                ]
            """)
        )
    )}
    var cameraPositionState = rememberCameraPositionState()
    var selectedPlace by remember { mutableStateOf<PlaceItem?>(null) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showFilterOptions by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Усі") }

    // Sample places data with categories
    val places = remember {
        mutableStateListOf(
            PlaceItem("1", "Starbucks Coffee", "123 Main St, Kyiv", "0.3 км", LatLng(50.450001, 30.523333), "Кафе"),
            PlaceItem("2", "ПУЗАТА ХАТА Restaurant", "456 Park Ave, Kyiv", "0.7 км", LatLng(50.447853, 30.525731), "Ресторан"),
            PlaceItem("3", "National Museum", "789 Art Blvd, Kyiv", "1.2 км", LatLng(50.453000, 30.530000), "Музей"),
            PlaceItem("4", "Shevchenko Park", "321 Nature Way, Kyiv", "1.6 км", LatLng(50.442000, 30.518000), "Парк"),
            PlaceItem("5", "Kyiv Opera House", "100 Opera St, Kyiv", "2.1 км", LatLng(50.446667, 30.513333), "Культура"),
            PlaceItem("6", "Gulliver Mall", "1 Shopping Ave, Kyiv", "0.9 км", LatLng(50.439167, 30.520833), "Шопінг")
        )
    }

    // Categories for filtering
    val categories = listOf("Усі", "Кафе", "Ресторан", "Музей", "Парк", "Культура", "Шопінг", "Інше")

    // Define default location (Kyiv)
    val defaultLocation = LatLng(50.450001, 30.523333)

    // Initialize map camera position
    LaunchedEffect(Unit) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
    }

    // Location permission handling
    val locationPermissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        locationPermissionState.value = isGranted
        mapProperties = mapProperties.copy(isMyLocationEnabled = isGranted)
    }

    // Filter places based on search query and category
    val filteredPlaces = if (searchQuery.isEmpty() && selectedCategory == "Усі") {
        places
    } else if (selectedCategory == "Усі") {
        places.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.address.contains(searchQuery, ignoreCase = true)
        }
    } else if (searchQuery.isEmpty()) {
        places.filter { it.category == selectedCategory }
    } else {
        places.filter {
            (it.name.contains(searchQuery, ignoreCase = true) ||
                    it.address.contains(searchQuery, ignoreCase = true)) &&
                    it.category == selectedCategory
        }
    }

    // Main layout with dark theme
    Surface(color = darkBackground) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Full screen map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                onMapClick = { latLng ->
                    // Add a custom marker when user clicks on the map
                    val newPlace = PlaceItem(
                        id = "custom_${System.currentTimeMillis()}",
                        name = "Власна локація",
                        address = "Координати: ${String.format("%.4f", latLng.latitude)}, ${String.format("%.4f", latLng.longitude)}",
                        distance = "N/A",
                        position = latLng
                    )
                    places.add(newPlace)
                    selectedPlace = newPlace
                    showBottomSheet = true
                }
            ) {
                // Display all markers on the map
                filteredPlaces.forEach { place ->
                    Marker(
                        state = rememberMarkerState(position = place.position),
                        title = place.name,
                        snippet = place.address,
                        onClick = {
                            selectedPlace = place
                            showBottomSheet = true
                            // Move camera to the selected place
                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLng(place.position)
                                )
                            }
                            true
                        }
                    )
                }
            }

            // Top search and filter bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = cardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Search bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Знайти",
                                tint = pureWhite,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.Transparent),
                                placeholder = { Text("Знайти локацію...", color = pureWhite.copy(alpha = 0.7f)) },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    focusedTextColor = pureWhite,
                                    cursorColor = accentBlue,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true
                            )

                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = "Очистити пошук",
                                        tint = pureWhite
                                    )
                                }
                            }

                            IconButton(onClick = { showFilterOptions = !showFilterOptions }) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Filters",
                                    tint = pureWhite
                                )
                            }
                        }

                        // Filter options
                        AnimatedVisibility(
                            visible = showFilterOptions,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Text(
                                    "Категорії:",
                                    color = pureWhite,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Row(
                                    modifier = Modifier.horizontalScroll(androidx.compose.foundation.rememberScrollState())
                                ) {
                                    categories.forEach { category ->
                                        FilterChip(
                                            selected = category == selectedCategory,
                                            onClick = { selectedCategory = category },
                                            label = { Text(category) },
                                            modifier = Modifier.padding(end = 8.dp),
                                            colors = FilterChipDefaults.filterChipColors(
                                                containerColor = darkBlue,
                                                labelColor = pureWhite,
                                                selectedContainerColor = accentBlue,
                                                selectedLabelColor = pureWhite
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Results count indicator
                AnimatedVisibility(
                    visible = filteredPlaces.isNotEmpty(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(
                        text = "Знайдено локацій: ${filteredPlaces.size}",
                        color = softWhite,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Action buttons
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Show list button
                FloatingActionButton(
                    onClick = { showBottomSheet = true },
                    containerColor = cardBackground,
                    contentColor = pureWhite
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Список місць"
                    )
                }

                // Location FAB
                FloatingActionButton(
                    onClick = {
                        // Request location permission if not granted
                        if (!locationPermissionState.value) {
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        } else {
                            // Use user location if available
                            userLocation?.let { location ->
                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLngZoom(location, 15f)
                                    )
                                }
                            }
                        }
                    },
                    containerColor = accentBlue,
                    contentColor = pureWhite
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Моя локація"
                    )
                }
            }

            // Bottom sheet for place details and list
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    containerColor = darkBackground,
                    sheetState = rememberModalBottomSheetState(),
                    dragHandle = { BottomSheetDefaults.DragHandle(color = softWhite) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Selected place details (if any)
                        selectedPlace?.let { place ->
                            PlaceDetailCard(
                                place = place,
                                backgroundColor = cardBackground,
                                accentColor = accentBlue,
                                textColor = pureWhite,
                                onNavigateClick = {
                                    // Navigation logic would go here
                                    showBottomSheet = false
                                }
                            )

                            Divider(
                                color = midBlue,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }

                        // Title for list
                        Text(
                            text = "Усі локації поблизу",
                            color = pureWhite,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // List of places
                        LazyColumn(
                            modifier = Modifier.height(400.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredPlaces) { place ->
                                SearchResultItem(
                                    place = place,
                                    isSelected = selectedPlace?.id == place.id,
                                    onClick = {
                                        selectedPlace = place
                                        coroutineScope.launch {
                                            cameraPositionState.animate(
                                                CameraUpdateFactory.newLatLngZoom(place.position, 15f)
                                            )
                                        }
                                    },
                                    backgroundColor = cardBackground,
                                    selectedColor = midBlue,
                                    textColor = pureWhite,
                                    accentColor = accentBlue
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceDetailCard(
    place: PlaceItem,
    backgroundColor: Color,
    accentColor: Color,
    textColor: Color,
    onNavigateClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with Category Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(accentColor.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = place.category,
                        color = accentColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Address with icon
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier
                        .padding(top = 2.dp, end = 8.dp)
                        .size(18.dp)
                )
                Text(
                    text = place.address,
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Distance with icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DirectionsRun,
                    contentDescription = "Run",
                    tint = accentColor,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(18.dp)
                )
                Text(
                    text = if (place.distance != "N/A") "Відстань: ${place.distance}" else "Відстань невідома",
                    color = textColor.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { /* Would save to favorites */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = accentColor
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(accentColor)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Зберегти")
                }

                Button(
                    onClick = onNavigateClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.NearMe,
                        contentDescription = "Navigate",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Маршрут")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultItem(
    place: PlaceItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    selectedColor: Color,
    textColor: Color,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) selectedColor else backgroundColor
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category indicator dot
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(accentColor)
                    .padding(end = 8.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = place.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = place.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = accentColor,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = "•",
                        color = textColor.copy(alpha = 0.5f)
                    )

                    Text(
                        text = place.distance,
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor.copy(alpha = 0.5f),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.1f))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Вибрати",
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}