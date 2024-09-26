package com.example.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        MediaController 는 쓰기가 어렵고 부족한 편이다.
        따라서 OTT(Coupang play, Tving)는 MediaController 를 사용하지 않고 Exoplayer 를 사용한다. (Netflix 는 자체 구현함)
        ExoPlayer : 구글에서 만든 신뢰할만한 외부 라이브러리(dependency 추가해야함)
        ★ 영상을 본격적으로 내 앱에 활용하겠다 -> MediaController 가 아닌 ExoPlayer 라이브러리를 사용하는 것 권장!
        ExoPlayer 는 기능이 다양하다. 사용하기가 좀 더 편리하다. DRM을 제공함(디지털 저작권 관리)
        스트리밍을 구현할 때 ExoPlayer 라이브러리를 사용하면 좋다.
        영상쪽에 관심이 있으면 해당 라이브러리 활용하는 것 공부해보는 것 추천!
         */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val videoUrl = intent.getStringExtra("video_url")
        val mediaController = MediaController(this@VideoActivity)

        // 여기서는 view binding을 쓸 이유가 없음
        val video = findViewById<VideoView>(R.id.video)
        video.setVideoPath(videoUrl)
        video.requestFocus()
        video.start()
        mediaController.show()
    }
}