package com.example.instagram

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import com.example.instagram.network.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class Utils {
    // retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://mellowcode.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService = retrofit.create(NetworkService::class.java)
}