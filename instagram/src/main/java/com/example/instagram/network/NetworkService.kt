package com.example.instagram.network

import com.example.instagram.LoginToken
import com.example.instagram.PostItem
import com.example.instagram.SignupToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @Multipart // 이미지를 서버에 보낼때 필요한 어노테이션
    @POST("instagram/post/")
    fun uploadInstaPost(
        @HeaderMap headers: Map<String, String>, // 사용자 토큰 -> 헤더
        @Part image: MultipartBody.Part, // 이미지는 쪼개서(part) 여러개(multi)로 보내야 한다.
        @Part("content") content: RequestBody // 게시물 내용
    ): Call<Any>
}