package com.example.instagram.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instagram.network.NetworkService
import com.example.instagram.SignupToken
import com.example.instagram.Utils
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

        binding.btnJoin.setOnClickListener {
            // 비밀번호 예외 처리
            val username = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordCheck = binding.etPasswordCheck.text.toString()
            if(username.isBlank()||password.isBlank()||passwordCheck.isBlank()) {
                Toast.makeText(this@InstaJoinActivity, "정보를 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else if(password!=passwordCheck) {
                Toast.makeText(this@InstaJoinActivity, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show()
            } else {
                // 서버에 post 로 저장하기
                val userInfo = HashMap<String, Any>()
                userInfo.put("username", username)
                userInfo.put("password1", password)
                userInfo.put("password2", passwordCheck)
                Utils().retrofitService.registerUserInfo(userInfo).enqueue(object: Callback<SignupToken> {
                    override fun onResponse(call: Call<SignupToken>, response: Response<SignupToken>) {
                        Log.d("signup", response.message())
                        if (response.isSuccessful) {
                            val response = response.body()
                            if(response?.token == null) {
                                Toast.makeText(this@InstaJoinActivity, "이미 존재하는 회원정보입니다!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@InstaJoinActivity, "가입이 완료되셨습니다!", Toast.LENGTH_SHORT).show()
                                intent.putExtra("new_id", username)
                                intent.putExtra("new_password", password)
                                setResult(RESULT_OK, intent)
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<SignupToken>, t: Throwable) {
                        Log.d("onFailure", t.message!!)
                        Toast.makeText(this@InstaJoinActivity, "서버 통신이 불안정합니다", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }
}