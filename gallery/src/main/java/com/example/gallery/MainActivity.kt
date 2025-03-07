package com.example.gallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListeners()
    }

    private fun initListeners() = with(binding) {
        btnLoadImage.setOnClickListener {
            checkGalleryPermission()
        }
    }

    private fun checkGalleryPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("ttest", "one")
                loadImage()
            }

            shouldShowRequestPermissionRationale(
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                Log.d("ttest", "two")
                showPermissionInfoDialog()
            }

            else -> {
                Log.d("ttest", "three")
                requestReadExternalStorage()
            }
        }
    }

    // 교육용 팝업
    private fun showPermissionInfoDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("이미지를 가져오기 위해서는 외부 저장소 읽기 권한이 필요합니다.")
            setNegativeButton("거절", null)
            setPositiveButton("동의") { _,_ ->
                requestReadExternalStorage()
            }
        }.show()
    }

    // 시스템 팝업
    private fun requestReadExternalStorage() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_READ_EXTERNAL_STORAGE
        )
    }

    // 갤러리에서 이미지 불러오기
    private fun loadImage() {
        Toast.makeText(this, "권한이 허용되었습니다!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }
}