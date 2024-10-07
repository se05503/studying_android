package com.example.melone

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class MelonResponse(
    val results: ArrayList<MelonItem>
): Serializable

data class MelonItem(
    @SerializedName("artist_name") val artistName: String,
    val image: String,
    val tracks: ArrayList<TrackItem>
): Serializable

data class TrackItem(
    val name: String, // 해당 artist 의 곡 이름
    val audio: String // mp31
): Serializable
