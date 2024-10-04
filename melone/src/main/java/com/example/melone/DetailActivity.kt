package com.example.melone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.melone.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thumbnail = intent.getStringExtra("thumbnail")
        val title = intent.getStringExtra("title")
        val audio = intent.getStringExtra("audio")

        binding.tvSongTitle.text = title
        Glide.with(this@DetailActivity).load(thumbnail).into(binding.ivSongThumbnail)
    }
}