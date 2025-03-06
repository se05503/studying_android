package com.example.usic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("onReceive", "${intent.action}")
        when(intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(context, "충전기가 연결되었어요🙂", Toast.LENGTH_SHORT).show()
            }
        }
    }
}