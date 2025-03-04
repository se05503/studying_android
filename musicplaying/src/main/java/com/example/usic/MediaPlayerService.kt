package com.example.usic

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer ?= null

    override fun onBind(intent: Intent): IBinder? {
        // 바인드 서비스가 아닌 포그라운드 서비스를 실습하기 때문에 해당 함수는 구현 x
        return null
    }

    // 서비스가 실행되고 onCreate 생명주기가 시작될 때 호출되는 함수
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            MEDIA_PLAYER_PLAY -> {
                if(mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(baseContext, R.raw.popsong).apply {
                        isLooping = true
                    }
                }
                mediaPlayer?.start()
            }
            MEDIA_PLAYER_PAUSE -> {
                mediaPlayer?.pause()
            }
            MEDIA_PLAYER_STOP -> {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                stopSelf() // 서비스 종료 → 명시적으로 종료해주지 않으면 서비스는 계속 백그라운드에서 실행되기 때문에 리소스를 차지하게 된다.
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}