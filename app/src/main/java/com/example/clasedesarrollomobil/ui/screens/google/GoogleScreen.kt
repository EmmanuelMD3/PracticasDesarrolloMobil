package com.example.clasedesarrollomobil.ui.screens.google

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.clasedesarrollomobil.BuildConfig
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GoogleScreen(onBackToMenu: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            MobileAds.initialize(context) {}
        }
    }

    DemoScreenScaffold(
        title = "Google",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Google Map View",
            description = "Mapa preparado con Google Maps Compose. La API key se lee desde local.properties."
        ) {
            if (BuildConfig.MAPS_API_KEY.isBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Agrega MAPS_API_KEY en local.properties para renderizar el mapa.")
                }
            } else {
                GoogleMapDemo()
            }
        }

        DemoCard(
            title = "Google Ad View",
            description = "Banner AdMob usando exclusivamente el ID oficial de anuncio de prueba."
        ) {
            BannerAdView()
        }
    }
}

@Composable
private fun GoogleMapDemo() {
    val context = LocalContext.current
    val uaemex = LatLng(19.2826, -99.6557)
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
            view.getMapAsync { googleMap ->
                googleMap.uiSettings.isZoomControlsEnabled = true
                googleMap.clear()
                googleMap.addMarker(
                    MarkerOptions()
                        .position(uaemex)
                        .title("UAEMex")
                        .snippet("Ubicación demostrativa")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uaemex, 13f))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    )
}

@Composable
private fun BannerAdView() {
    val context = LocalContext.current
    val adView = remember {
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = TEST_BANNER_AD_UNIT_ID
            loadAd(AdRequest.Builder().build())
        }
    }

    DisposableEffect(adView) {
        onDispose {
            adView.destroy()
        }
    }

    AndroidView(
        factory = { adView },
        modifier = Modifier.fillMaxWidth()
    )
}

private const val TEST_BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
