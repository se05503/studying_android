package com.example.usic

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.usic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListeners()
    }

    private fun initListeners() = with(binding) {
        ivPlay.setOnClickListener {
            play()
        }
        ivPause.setOnClickListener {
            pause()
        }
        ivStop.setOnClickListener {
            stop()
        }
    }

    private fun play() {
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.popsong).apply {
                isLooping = true
            }
        }
        mediaPlayer?.start()
    }

    private fun pause() {
        mediaPlayer?.pause()
    }

    private fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release() // 더이상 사용하지 않는 경우 메모리에서 해제
        mediaPlayer = null
    }
}