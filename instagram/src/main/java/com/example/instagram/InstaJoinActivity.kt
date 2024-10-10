package com.example.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instagram.databinding.ActivityInstaJoinBinding
import com.google.android.material.snackbar.Snackbar

class InstaJoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityInstaJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstaJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                // 서버에 pst 로 저장하기
                intent.putExtra("new_id", id)
                intent.putExtra("new_password", password)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}