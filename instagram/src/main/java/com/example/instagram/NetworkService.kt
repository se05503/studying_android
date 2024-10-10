package com.example.instagram

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkService {
    @POST("user/signup/")
    fun registerUserInfo(
        @Body params: HashMap<String, Any>
    ): Call<Token>
}