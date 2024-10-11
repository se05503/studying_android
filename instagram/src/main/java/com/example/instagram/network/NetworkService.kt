package com.example.instagram.network

import com.example.instagram.SignupToken
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkService {
    @POST("user/signup/")
    @FormUrlEncoded
    fun registerUserInfo(
        @FieldMap params: HashMap<String, Any>
    ): Call<SignupToken>
}