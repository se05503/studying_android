package com.example.melone

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.melone.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thumbnail = intent.getStringExtra("thumbnail")
        val title = intent.getStringExtra("title")
        val audio = intent.getStringExtra("audio")
        var isPlaying = false

        binding.tvSongTitle.text = title
        Glide.with(this@DetailActivity).load(thumbnail).into(binding.ivSongThumbnail)

        binding.ivPlay.setOnClickListener {
            when (isPlaying) {
                true -> {
                    isPlaying = false
                    binding.ivPlay.setImageResource(R.drawable.ic_play)
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                false -> {
                    isPlaying = true
                    binding.ivPlay.setImageResource(R.drawable.ic_stop)
                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )
                        mediaPlayer?.setDataSource(audio)
                        mediaPlayer?.prepare() // might take long! (for buffering, etc)
                        mediaPlayer?.start()
                    }
                }
            }
        }
    }
}