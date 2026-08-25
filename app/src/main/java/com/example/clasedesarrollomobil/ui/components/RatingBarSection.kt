package com.example.clasedesarrollomobil.ui.components

import android.widget.RatingBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal fun RatingBarSection(
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    DemoSection(
        title = "3. RatingBar",
        description = "Permite realizar una calificación mediante estrellas."
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AndroidView(
                factory = { context ->
                    RatingBar(
                        context,
                        null,
                        android.R.attr.ratingBarStyle
                    ).apply {
                        numStars = 5
                        stepSize = 1f
                        this.rating = rating

                        // Listener del RatingBar para reflejar la calificación seleccionada.
                        setOnRatingBarChangeListener { view, value, _ ->
                            val fixedRating = value.coerceIn(1f, 5f)
                            if (fixedRating != value) view.rating = fixedRating
                            onRatingChanged(fixedRating)
                        }
                    }
                },
                update = { view ->
                    if (view.rating != rating) view.rating = rating
                },
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Calificación: ${rating.toInt()} de 5 estrellas",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
