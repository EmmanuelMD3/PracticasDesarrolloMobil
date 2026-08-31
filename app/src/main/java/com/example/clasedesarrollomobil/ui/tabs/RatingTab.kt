package com.example.clasedesarrollomobil.ui.tabs

import android.widget.RatingBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun RatingTab() {
    var rating by remember { mutableFloatStateOf(3f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AndroidView(
            factory = { context ->
                RatingBar(context, null, android.R.attr.ratingBarStyle).apply {
                    numStars = 5
                    stepSize = 1f
                    this.rating = rating
                    setOnRatingBarChangeListener { _, value, _ ->
                        rating = value.coerceIn(1f, 5f)
                    }
                }
            },
            update = { view ->
                if (view.rating != rating) view.rating = rating
            }
        )
        Text("Calificación: ${rating.toInt()} de 5")
    }
}
