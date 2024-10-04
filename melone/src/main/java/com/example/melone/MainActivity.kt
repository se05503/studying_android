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
            .baseUrl("https://api.jamendo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(NetworkService::class.java)
        retrofitService.getMelonItems(clientId = "your_key", artistName = "WE ARE FM")
            .enqueue(object : Callback<MelonResponse> {
                override fun onResponse(
                    call: Call<MelonResponse>,
                    response: Response<MelonResponse>
                ) {
                    if(response.isSuccessful) {
                        val melonResponse = response.body()
                        binding.recyclerview.apply {
                            adapter = MelonAdapter(
                                melonItems = melonResponse!!.results,
                                context = this@MainActivity,
                                glide = Glide.with(this@MainActivity),
                                inflater = LayoutInflater.from(this@MainActivity)
                            )
                        }
                        binding.tvArtistName.text = melonResponse!!.results[0].artistName
                    }
                }

                override fun onFailure(call: Call<MelonResponse>, t: Throwable) {
                    Log.d("server response", t.message!!)
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