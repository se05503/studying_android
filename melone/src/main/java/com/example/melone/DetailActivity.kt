package com.example.melone

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.melone.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var mediaPlayer: MediaPlayer
    lateinit var melonItems: ArrayList<MelonItem>

    // setter 사용 -> 변수 상태를 따로 관리하여 유지보수를 용이하게 한다. 변수와 뷰를 바인딩시킨다.
    // 액티비티의 생명주기에 간섭받지 않는다. 상태가 바뀌면 바로 뷰에 반영된다.
    var isPlaying = false
        set(value) {
            if (value) {
                binding.ivPlay.setImageDrawable(
                    this.resources.getDrawable(
                        R.drawable.ic_stop,
                        this.theme
                    )
                )
            } else {
                binding.ivPlay.setImageDrawable(
                    this.resources.getDrawable(
                        R.drawable.ic_play,
                        this.theme
                    )
                )
            }
            field = value
        }

    private var currentPosition = 0
        set(value) {
            if (value < 0) field = 0
            else if(value == melonItems[0].tracks.size) field = melonItems[0].tracks.size-1
            else field = value // 이것 빼먹으면 안됨
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("detailActivity", "onCreate")
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // adapter에서 분해되서 온 재료를 ArrayList<MelonItem> 형태로 만들겠다!
        melonItems = intent.getSerializableExtra("melonItems") as ArrayList<MelonItem>
        currentPosition = intent.getIntExtra("current_position", -1)

        binding.tvSongTitle.text = melonItems[0].tracks[currentPosition].name
        Glide.with(this@DetailActivity).load(melonItems[0].image).into(binding.ivSongThumbnail)

        // 재생 버튼을 누르지 않은 상태에서 다음 버튼이나 이전 버튼을 누르게 되면
        // 초기화되지 않은(lateinit) mediaPlayer 을 호출하게 되는 문제를 setOnClickListener 밖으로 빼면서 해결
        mediaPlayer = MediaPlayer.create(
            this@DetailActivity,
            Uri.parse(melonItems[0].tracks[currentPosition].audio)
        )

        binding.ivPlay.setOnClickListener {
            if (isPlaying) {
                isPlaying = false
                mediaPlayer.pause()
            } else {
                isPlaying = true
                playMelonItem(melonItems[0].tracks[currentPosition].audio)
            }
        }

        binding.ivPlayBack.setOnClickListener {
            isPlaying = false
            mediaPlayer.release()
            currentPosition -= 1
            binding.tvSongTitle.text = melonItems[0].tracks[currentPosition].name
        }

        binding.ivPlayNext.setOnClickListener {
            isPlaying = false
            mediaPlayer.release()
            currentPosition += 1
            binding.tvSongTitle.text = melonItems[0].tracks[currentPosition].name
        }
    }

    private fun playMelonItem(audio: String) {
        mediaPlayer = MediaPlayer.create(this@DetailActivity, Uri.parse(audio))
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        Log.d("detailActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("detailActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("detailActivity", "onDestroy")
        mediaPlayer.release()
    }
}