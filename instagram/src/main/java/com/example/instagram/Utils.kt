package com.example.instagram

import com.example.instagram.network.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Utils {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://mellowcode.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService = retrofit.create(NetworkService::class.java)
}