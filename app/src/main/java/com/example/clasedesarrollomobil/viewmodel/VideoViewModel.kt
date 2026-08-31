package com.example.clasedesarrollomobil.viewmodel

import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import com.example.clasedesarrollomobil.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VideoOption(
    val title: String,
    @param:RawRes val resourceId: Int
)

class VideoViewModel : ViewModel() {
    val videos = listOf(
        VideoOption("Video 1", R.raw.video1),
        VideoOption("Video 2", R.raw.video2),
        VideoOption("Video 3", R.raw.video3)
    )

    private val _selectedVideo = MutableStateFlow(videos.first())
    val selectedVideo: StateFlow<VideoOption> = _selectedVideo.asStateFlow()

    fun selectVideo(video: VideoOption) {
        _selectedVideo.value = video
    }
}
