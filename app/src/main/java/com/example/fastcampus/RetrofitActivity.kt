package com.example.fastcampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit

/*
Retrofit : 안드로이드에서 기본적으로 제공해주는 라이브러리가 아님 -> 직접 추가해야함
Retrofit 을 이용해 주소에 요청한 다음 학생 데이터를 리스트로 받는 것
baseUrl : 무조건 존재하는 url의 일부
돈을 지불하고 url(domain)을 살 수가 있다. -> baseUrl만 사는 것이다.
baseUrl은 .(점)을 기준으로 판단한다.
ex) https://mellowcode.org/json/students/ 의 baseUrl -> https://mellowcode.org/
addConverterFactory : 서버가 클라이언트에 데이터를 보낼 때, 알아들을 수 없는 데이터를 json 형태로 바꿔야 한다(convert).
누가 convert 할 것인가? -> Gson
 */
class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mellowcode.org/")
            .addConverterFactory()
    }
}