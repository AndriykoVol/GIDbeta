package com.example.gid.ui.theme.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

data class PlaceItem(
    val id: String,
    val name: String,
    val address: String,
    val distance: String,
    val position: LatLng
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // State variables
    var searchQuery by remember { mutableStateOf("") }
    var mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = false)) }
    var cameraPositionState = rememberCameraPositionState()
    var selectedPlace by remember { mutableStateOf<PlaceItem?>(null) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Sample places data - in a real app, this would come from an API
    val places = remember {
        mutableStateListOf(
            PlaceItem("1", "Starbucks Coffee", "123 Main St, Kyiv", "0.3 км", LatLng(50.450001, 30.523333)),
            PlaceItem("2", "ПУЗАТА ХАТА Restaurant", "456 Park Ave, Kyiv", "0.7 км", LatLng(50.447853, 30.525731)),
            PlaceItem("3", "National Museum", "789 Art Blvd, Kyiv", "1.2 км", LatLng(50.453000, 30.530000)),
            PlaceItem("4", "Shevchenko Park", "321 Nature Way, Kyiv", "1.6 км", LatLng(50.442000, 30.518000))
        )
    }

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

    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Мапа",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Знайти локацію...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Знайти"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            singleLine = true
        )

        // Main content - Map and Results
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Map area (Upper half)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Google Map
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,
                    onMapClick = { latLng ->
                        // Add a custom marker when user clicks on the map
                        val newPlace = PlaceItem(
                            id = "custom_${System.currentTimeMillis()}",
                            name = "Custom Location",
                            address = "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}",
                            distance = "N/A",
                            position = latLng
                        )
                        places.add(newPlace)
                        selectedPlace = newPlace
                    }
                ) {
                    // Display all markers on the map
                    places.forEach { place ->
                        Marker(
                            state = rememberMarkerState(position = place.position),
                            title = place.name,
                            snippet = place.address,
                            onClick = {
                                selectedPlace = place
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

                // Location FAB
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
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
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "My Location"
                        )
                    }
                }
            }

            // Search results list (Lower half)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = "Результати пошуку",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Filter places based on search query
                val filteredPlaces = if (searchQuery.isEmpty()) {
                    places
                } else {
                    places.filter {
                        it.name.contains(searchQuery, ignoreCase = true) ||
                                it.address.contains(searchQuery, ignoreCase = true)
                    }
                }

                LazyColumn {
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
                            }
                        )
                    }
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
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = place.address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = place.distance,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}