package com.example.melone

import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {
    @GET("melon/list/")
    fun getMelonItems(): Call<ArrayList<MelonItem>>
}