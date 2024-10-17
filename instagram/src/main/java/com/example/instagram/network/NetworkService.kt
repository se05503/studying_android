package com.example.instagram.network

import com.example.instagram.LoginToken
import com.example.instagram.PostItem
import com.example.instagram.SignupToken
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkService {
    @POST("user/signup/")
    @FormUrlEncoded
    fun registerUserInfo(
        @FieldMap params: HashMap<String, Any>
    ): Call<SignupToken>

    @POST("user/login/")
    @FormUrlEncoded // 이거 왜 붙이는거였지?
    fun checkUserLoginInfo(
        @FieldMap params: HashMap<String, Any> // @FieldMap 이 무슨 의미였지?
    ): Call<LoginToken>

    @GET("instagram/post/list/all/")
    fun getInstaPosts(): Call<List<PostItem>>
}