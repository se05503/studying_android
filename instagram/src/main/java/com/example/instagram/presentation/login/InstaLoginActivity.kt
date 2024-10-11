package com.example.instagram.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.databinding.ActivityInstaLoginBinding
import com.example.instagram.presentation.home.InstaMainActivity
import java.io.File

class InstaLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityInstaLoginBinding
    lateinit var requestLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            val intent = Intent(this@InstaLoginActivity, InstaMainActivity::class.java)
            startActivity(intent)
            // 해당 부분 토큰과 연결시켜서 다시 구현하기
        }

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

    }

}