package com.example.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

class InstaJoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_join)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }
}