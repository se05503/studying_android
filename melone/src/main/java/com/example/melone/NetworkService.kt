package com.example.melone

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("v3.0/albums/tracks/")
    fun getMelonItems(
        @Query("client_id") clientId: String,
        @Query("artist_name") artistName: String
    ): Call<MelonResponse>
}