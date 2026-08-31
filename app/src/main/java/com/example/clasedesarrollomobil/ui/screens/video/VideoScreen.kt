package com.example.clasedesarrollomobil.ui.screens.video

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.clasedesarrollomobil.ui.components.DemoCard
import com.example.clasedesarrollomobil.ui.components.DemoScreenScaffold
import com.example.clasedesarrollomobil.viewmodel.VideoViewModel

@Composable
fun VideoScreen(
    onBackToMenu: () -> Unit,
    viewModel: VideoViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedVideo by viewModel.selectedVideo.collectAsState()
    var volume by rememberSaveable { mutableFloatStateOf(1f) }
    var lastAudibleVolume by rememberSaveable { mutableFloatStateOf(1f) }
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                    .build(),
                true
            )
        }
    }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    LaunchedEffect(selectedVideo) {
        val videoUri = Uri.parse(
            "android.resource://${context.packageName}/${selectedVideo.resourceId}"
        )
        player.setMediaItem(MediaItem.fromUri(videoUri))
        player.prepare()
    }

    LaunchedEffect(volume) {
        player.volume = volume
    }

    DemoScreenScaffold(
        title = "Video",
        onBackToMenu = onBackToMenu
    ) {
        DemoCard(
            title = "Reproductor embebido",
            description = "Media3 ExoPlayer reproduce tres opciones dentro de la app con controles de avance, pausa y posición."
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                viewModel.videos.forEach { video ->
                    FilterChip(
                        selected = selectedVideo == video,
                        onClick = { viewModel.selectVideo(video) },
                        label = { Text(video.title) }
                    )
                }
            }
            Text("Seleccionado: ${selectedVideo.title}")

            AndroidView(
                factory = { viewContext ->
                    PlayerView(viewContext).apply {
                        this.player = player
                        useController = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (volume > 0f) {
                            lastAudibleVolume = volume
                            volume = 0f
                        } else {
                            volume = lastAudibleVolume.coerceAtLeast(0.25f)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (volume == 0f) {
                            Icons.AutoMirrored.Filled.VolumeOff
                        } else {
                            Icons.AutoMirrored.Filled.VolumeUp
                        },
                        contentDescription = if (volume == 0f) "Activar sonido" else "Silenciar"
                    )
                }
                Slider(
                    value = volume,
                    onValueChange = { newVolume ->
                        volume = newVolume
                        if (newVolume > 0f) lastAudibleVolume = newVolume
                    },
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${(volume * 100).toInt()}%",
                    modifier = Modifier.width(48.dp)
                )
            }
        }
    }
}
