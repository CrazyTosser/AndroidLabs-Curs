package com.example.lab07_2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

const val MESSAGE_EXTRA = "_MSG_"
const val BROADCAST_TAG = "BLABLABLA_MY_SUPER_COOL_TAG"

class MainActivity : AppCompatActivity() {
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val msg = intent?.getStringExtra(MESSAGE_EXTRA)
                text.text = msg
            }
        }

        registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_TAG))

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}
