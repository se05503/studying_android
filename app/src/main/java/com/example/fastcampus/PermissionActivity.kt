package com.example.fastcampus

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/*
requestCode : 내 요청을 식별하기 위한 임의의 값 (아무값을 넣어도 됨)
권한 요청을 보낸 후 사용자의 응답을 알 필요가 있다.
 */
class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        findViewById<TextView>(R.id.askPermission).setOnClickListener {
            // cameraPermission : 카메라 권한에 대한 상태
            val cameraPermission = ContextCompat.checkSelfPermission(
                this@PermissionActivity,
                android.Manifest.permission.CAMERA
            )
            if(cameraPermission != PackageManager.PERMISSION_GRANTED) {
                // 권한이 없는 경우 -> 권한 요청
                ActivityCompat.requestPermissions(
                    this@PermissionActivity,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1000
                )
            } else {
                // 권한이 있는 경우
                Log.d("Camera permission", "already exist")
            }
        }
    }

    // 권한 요청에 사용자의 응답이 호출됨
    // grantResults : 획득한 권한이 있음 -> 요청을 배열 형태로 보
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1000) {
            // 우리가 보낸 권한 요청이 맞는가?
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 카메라 요청에 대한 권한을 사용자가 승낙한 경우
                Log.d("User Response","Accept")
            } else {
                // 카메라 요청에 대한 권한을 사용자가 승낙하지 않은 경우
                Log.d("User Response","Denied")
            }
        }
    }
}