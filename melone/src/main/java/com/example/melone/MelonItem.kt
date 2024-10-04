package com.example.melone

import com.google.gson.annotations.SerializedName

data class MelonResponse(
    val results: List<MelonItem>
)

data class MelonItem(
    @SerializedName("artist_name") val artistName: String,
    val image: String,
    val tracks: List<TrackItem>
)

data class TrackItem(
    val name: String, // 해당 artist 의 곡 이름
    val audio: String // mp31
)
