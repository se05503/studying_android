package com.example.usic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.IBinder

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer ?= null

    override fun onBind(intent: Intent): IBinder? {
        // 바인드 서비스가 아닌 포그라운드 서비스를 실습하기 때문에 해당 함수는 구현 x
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val playIcon = Icon.createWithResource(baseContext, R.drawable.ic_play)
        val pauseIcon = Icon.createWithResource(baseContext, R.drawable.ic_pause)
        val stopIcon = Icon.createWithResource(baseContext, R.drawable.ic_stop)

        val playPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PLAY },
            PendingIntent.FLAG_IMMUTABLE
        )

        val pausePendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopPendingIntent = PendingIntent.getService(
            baseContext,
            0,
            Intent(baseContext, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )

        val mainPendingIntent = PendingIntent.getActivity(
            baseContext,
            0,
            Intent(baseContext, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP }, // 기존 액티비티 재활용
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = Notification.Builder(baseContext, CHANNEL_ID)
            .setStyle(Notification.MediaStyle().setShowActionsInCompactView(0,1,2))
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_notification)
            .addAction(Notification.Action.Builder(playIcon, MEDIA_PLAYER_PLAY, playPendingIntent).build())
            .addAction(Notification.Action.Builder(pauseIcon, MEDIA_PLAYER_PAUSE, pausePendingIntent).build())
            .addAction(Notification.Action.Builder(stopIcon, MEDIA_PLAYER_STOP, stopPendingIntent).build())
            .setContentIntent(mainPendingIntent) // 알림을 눌렀을 때 수행할 동작
            .setContentTitle("음악재생 제목")
            .setContentText("음악재생 내용")
            .build()

        startForeground(100, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = baseContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
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

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}