package com.example.youtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                Log.d("onResponse", response.message())
                if(response.isSuccessful) {
                    val videoList = response.body()
                    binding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.recyclerview.adapter = YoutubeAdapter(videoList!!, LayoutInflater.from(this@MainActivity), contentResolver)
                    binding.recyclerview.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
                } else {
                    Log.d("server response : ","isNotSuccessful")
                }
            }

            override fun onFailure(call: Call<ArrayList<VideoItem>>, t: Throwable) {
                Log.d("server response : ","onFailure")
                Toast.makeText(this@MainActivity, "서버 연결이 불안정합니다", Toast.LENGTH_LONG).show()
            }

        })
    }
}