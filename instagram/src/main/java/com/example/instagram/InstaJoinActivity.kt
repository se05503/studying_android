package com.example.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instagram.databinding.ActivityInstaJoinBinding
import com.google.android.material.snackbar.Snackbar
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
                userInfo.put("password2", passwordCheck) //
                retrofitService.registerUserInfo(userInfo).enqueue(object: Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        TODO("Not yet implemented")
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

                // 화면 전환 시 데이터 넘겨주기
                intent.putExtra("new_id", id)
                intent.putExtra("new_password", password)
                setResult(RESULT_OK, intent)
                Toast.makeText(this@InstaJoinActivity, "가입이 완료되셨습니다!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}