package com.example.youtube

data class VideoItem(
    val id: Int,
    val title: String,
    val description: String,
    val video: String, // 확장자: mp4
    val thumbnail: String // 확장자: png
)