package com.example.melone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SecurityException resolve
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }
}