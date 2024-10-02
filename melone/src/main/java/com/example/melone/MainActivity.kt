package com.example.melone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.melone.databinding.ActivityMainBinding
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()

        // Network data 가져오기
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(NetworkService::class.java)
        retrofitService.getMelonItems().enqueue(object : Callback<ArrayList<MelonItem>> {
            override fun onResponse(
                call: Call<ArrayList<MelonItem>>,
                response: Response<ArrayList<MelonItem>>
            ) {
                Log.d("server response: ", response.message())
                val melonItems = response.body()
                binding.recyclerview.adapter = MelonAdapter(
                    melonItems!!,
                    this@MainActivity,
                    LayoutInflater.from(this@MainActivity),
                    Glide.with(this@MainActivity)
                )
            }

            override fun onFailure(call: Call<ArrayList<MelonItem>>, t: Throwable) {
                Log.d("server response: ", t.message!!)

            }
        })

        binding.ivPlayBack.setOnClickListener {

        }
        binding.ivPlay.setOnClickListener {

        }
        binding.ivPlayNext.setOnClickListener {

        }


    }
}