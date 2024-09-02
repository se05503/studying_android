package com.example.fastcampus

import retrofit2.Call
import retrofit2.http.GET

/*
Retrofit 으로 요청을 하는데, 요청을 할 때마다 받아오는 객체가 다르다. (요청을 한번만 하진 않는다.)
어떤 객체가 올건지 적어줌
 */

class StudentFromServer(
    val id: Int,
    val name: String,
    val age: Int,
    val intro: String
)

interface RetrofitService {
    // 요청을 보냄 -> Retrofit 은 Call 로 한번 감싸야 한다. (문법)
    @GET("json/students")
    fun getStudentList(): Call<ArrayList<StudentFromServer>>
}