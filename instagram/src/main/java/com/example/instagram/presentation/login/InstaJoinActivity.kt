package com.example.instagram.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instagram.network.NetworkService
import com.example.instagram.SignupToken
import com.example.instagram.databinding.ActivityInstaJoinBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstaJoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstaJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstaJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(NetworkService::class.java)

        binding.btnJoin.setOnClickListener {
            // 비밀번호 예외 처리
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordCheck = binding.etPasswordCheck.text.toString()
            if(id.isBlank()||password.isBlank()||passwordCheck.isBlank()) {
                Toast.makeText(this@InstaJoinActivity, "정보를 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else if(password!=passwordCheck) {
                Toast.makeText(this@InstaJoinActivity, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show()
            } else {
                // 서버에 post 로 저장하기
                val userInfo = HashMap<String, Any>()
                userInfo.put("username", id)
                userInfo.put("password1", password)
                userInfo.put("password2", passwordCheck)
                retrofitService.registerUserInfo(userInfo).enqueue(object: Callback<SignupToken> {
                    override fun onResponse(call: Call<SignupToken>, response: Response<SignupToken>) {
                        if (response.isSuccessful) {
                            // 서버에 새로운 아이디와 비밀번호 등록함
                            Toast.makeText(this@InstaJoinActivity, "가입이 완료되셨습니다!", Toast.LENGTH_SHORT).show()
                            val response = response.body()
                            val token = response?.token
                            Log.d("Sign Up Success", token!!)
                        }
                    }

                    override fun onFailure(call: Call<SignupToken>, t: Throwable) {
                        Log.d("onFailure", t.message!!)
                    }

                })

                // 화면 전환 시 데이터 넘겨주기
                intent.putExtra("new_id", id)
                intent.putExtra("new_password", password)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}