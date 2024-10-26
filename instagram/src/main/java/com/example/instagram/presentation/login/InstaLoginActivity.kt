package com.example.instagram.presentation.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.LoginToken
import com.example.instagram.Utils
import com.example.instagram.databinding.ActivityInstaLoginBinding
import com.example.instagram.presentation.home.InstaMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

class InstaLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityInstaLoginBinding
    lateinit var requestLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // SecurityException resolve
        codeCacheDir.setReadOnly()

        binding = ActivityInstaLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Contract 와 Callback 등록
        requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // if 문을 굳이 사용해야 하는지는 잘 모르겠음
            if(it.resultCode == RESULT_OK) {
                binding.etId.setText(it.data?.getStringExtra("new_id"))
                binding.etPassword.setText(it.data?.getStringExtra("new_password"))
            }
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@InstaLoginActivity, InstaJoinActivity::class.java)
            requestLauncher.launch(intent)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()

            if(username.isBlank()||password.isBlank()) {
                Toast.makeText(this@InstaLoginActivity, "정보를 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                val userInfo = HashMap<String,Any>()
                userInfo.put("username", username)
                userInfo.put("password", password)
                Utils().retrofitService.checkUserLoginInfo(userInfo).enqueue(object: Callback<LoginToken> {
                    override fun onResponse(call: Call<LoginToken>, response: Response<LoginToken>) {
                        Log.d("message", response.message())
                        if(response.isSuccessful) {
                            Toast.makeText(this@InstaLoginActivity, "로그인 성공했습니다!",Toast.LENGTH_SHORT).show()
                            val response = response.body()
                            val token = response?.token
                            val intent = Intent(this@InstaLoginActivity, InstaMainActivity::class.java)
//                            intent.putExtra("userToken", token)
                            // 앱 내부 저장소인 sharedpreference 에 현재 로그인한 유저의 정보(토큰)을 저장하자
                            // Utils 로 한번 빼볼려고 했는데 getSharedPreferences 가 접근이 안된다.
                            val sharedPreference = getSharedPreferences("user_token", Context.MODE_PRIVATE)
                            val editor = sharedPreference.edit()
                            editor.putString("current_login_user_token", token)
                            editor.commit()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@InstaLoginActivity, "회원 정보가 없습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginToken>, t: Throwable) {
                        // 와이파이를 연결하지 않은 상태로 서버 요청을 하는 경우
                        Log.d("onFailure", t.message!!)
                        Toast.makeText(this@InstaLoginActivity, "서버 연결이 불안정합니다!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

}