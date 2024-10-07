package com.example.melone

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.melone.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var mediaPlayer : MediaPlayer

    // setter 사용 -> 변수 상태를 따로 관리하여 유지보수를 용이하게 한다. 변수와 뷰를 바인딩시킨다.
    var isPlaying = false
        set(value) {
            if(value) {
                binding.ivPlay.setImageDrawable(this.resources.getDrawable(R.drawable.ic_stop, this.theme))
            } else {
                binding.ivPlay.setImageDrawable(this.resources.getDrawable(R.drawable.ic_play, this.theme))
            }
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("detailActivity","onCreate")
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // adapter에서 분해되서 온 재료를 ArrayList<MelonItem> 형태로 만들겠다!
        val melonItems = intent.getSerializableExtra("melonItems") as ArrayList<MelonItem>
        val currentPosition = intent.getIntExtra("current_position", -1)

//        mediaPlayer = MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .build()
//            )
//        }

//        val thumbnail = intent.getStringExtra("thumbnail")
//         ArrayList<TrackItem> 를 받아올 수 없다. (data class 를 받아올 수 없다) ArrayList<String> 형태로 받아와야 한다.
//        val titles = intent.getStringArrayListExtra("titles")
//        val audios = intent.getStringArrayListExtra("audios")


        binding.ivPlay.setOnClickListener {
            if(isPlaying) {
                isPlaying = false
                mediaPlayer.stop()
            } else {
                isPlaying = true
                playMelonItem(melonItems[0].tracks[currentPosition].audio)
            }
        }

//        binding.tvSongTitle.text = titles!![currentPosition]
//        Glide.with(this@DetailActivity).load(thumbnail).into(binding.ivSongThumbnail)
//
//        binding.ivPlay.setOnClickListener {
//            when (isPlaying) {
//                true -> {
//                    isPlaying = false
//                    binding.ivPlay.setImageResource(R.drawable.ic_play)
//                    mediaPlayer?.stop()
//                    mediaPlayer?.release()
//                    mediaPlayer = null
//                }
//                false -> {
//                    isPlaying = true
//                    binding.ivPlay.setImageResource(R.drawable.ic_stop)
//                    mediaPlayer = MediaPlayer().apply {
//                        setAudioAttributes(
//                            AudioAttributes.Builder()
//                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                                .setUsage(AudioAttributes.USAGE_MEDIA)
//                                .build()
//                        )
//                        mediaPlayer?.setDataSource(audios!![currentPosition])
//                        mediaPlayer?.prepare() // might take long! (for buffering, etc)
//                        mediaPlayer?.start()
//                    }
//                }
//            }
//        }
//
//        binding.ivPlayBack.setOnClickListener {
//            isPlaying = false
//            binding.tvSongTitle.text = titles[currentPosition-1]
//        }
//
//        binding.ivPlayNext.setOnClickListener {
//            isPlaying = false
//        }
    }

    private fun playMelonItem(audio: String) {
        mediaPlayer = MediaPlayer.create(this@DetailActivity, Uri.parse(audio))
        mediaPlayer.start()
    }
}