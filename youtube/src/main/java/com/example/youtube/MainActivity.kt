package com.example.youtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.youtube.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.getVideoList().enqueue(object : Callback<ArrayList<VideoItem>> {
            override fun onResponse(
                call: Call<ArrayList<VideoItem>>,
                response: Response<ArrayList<VideoItem>>
            ) {
                val videoList = response.body()
                val glide = Glide.with(this@MainActivity)
                // Activity 에서 Adapter 로 필요한 parameter(layout, context 등) 을 넣어준다.
                binding.recyclerview.adapter =
                    YoutubeAdapter(
                        videoList!!,
                        LayoutInflater.from(this@MainActivity),
                        glide,
                        this@MainActivity
                    )
            }

            override fun onFailure(call: Call<ArrayList<VideoItem>>, t: Throwable) {
                // t: 예외
                // t.message: 어떤 예외가 발생했는지 알려주는 메세지
                // CLEARTEXT communication to mellowcode.org not permitted by network security policy
                Log.d("server response : ", t.message!!)
                Toast.makeText(this@MainActivity, "서버 연결이 불안정합니다", Toast.LENGTH_LONG).show()
            }

        })
    }
}