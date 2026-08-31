package com.example.clasedesarrollomobil.ui.screens.gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clasedesarrollomobil.BuildConfig
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.example.clasedesarrollomobil.viewmodel.GpsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun GpsScreen(
    onBackToMenu: () -> Unit,
    viewModel: GpsViewModel = viewModel()
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val uiState by viewModel.uiState.collectAsState()
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (granted) {
                getCurrentLocation(fusedLocationClient, viewModel)
            } else {
                viewModel.onPermissionDenied()
            }
        }
    )

    DemoScreenScaffold(
        title = "GPS",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Ubicación actual",
            description = "Solicita permiso, obtiene latitud/longitud y centra el mapa."
        ) {
            Button(
                onClick = {
                    val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    if (fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED) {
                        getCurrentLocation(fusedLocationClient, viewModel)
                    } else {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }
            ) {
                Text("Obtener ubicación actual")
            }
            Text(uiState.message)
            Text("Latitud: ${uiState.latitude ?: "--"}")
            Text("Longitud: ${uiState.longitude ?: "--"}")
        }

        DemoCard(
            title = "Mapa",
            description = "Muestra marcador en la ubicación actual si la clave Maps está configurada."
        ) {
            if (BuildConfig.MAPS_API_KEY.isBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Configura MAPS_API_KEY para mostrar el mapa")
                }
            } else {
                LocationMap(
                    latitude = uiState.latitude,
                    longitude = uiState.longitude
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: GpsViewModel
) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                viewModel.onLocationReceived(location)
            } else {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    CancellationTokenSource().token
                ).addOnSuccessListener { currentLocation ->
                    viewModel.onLocationReceived(currentLocation)
                }.addOnFailureListener {
                    viewModel.onLocationReceived(null)
                }
            }
        }
        .addOnFailureListener {
            viewModel.onLocationReceived(null)
        }
}

@Composable
private fun LocationMap(latitude: Double?, longitude: Double?) {
    val context = LocalContext.current
    val fallback = LatLng(19.2826, -99.6557)
    val currentPosition = if (latitude != null && longitude != null) {
        LatLng(latitude, longitude)
    } else {
        fallback
    }
    val mapView = remember {
        MapView(context).apply {
            onCreate(null)
            onStart()
            onResume()
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }

    AndroidView(
        factory = { mapView },
        update = { view ->
            view.getMapAsync { map ->
                map.clear()
                map.uiSettings.isZoomControlsEnabled = true
                map.addMarker(
                    MarkerOptions()
                        .position(currentPosition)
                        .title(if (latitude == null) "UAEMex" else "Mi ubicación actual")
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15f))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    )
}
