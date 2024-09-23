package com.example.youtube

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("youtube/list/")
    fun getVideoList(): Call<ArrayList<VideoItem>>
}