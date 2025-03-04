package com.example.usic

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.usic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        // 서비스 실행
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PLAY }
        startService(intent)
    }

    private fun pause() {
        val intent = Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PAUSE }
        startService(intent)
    }

    private fun stop() {
        val intent = Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_STOP }
        startService(intent)
    }
}