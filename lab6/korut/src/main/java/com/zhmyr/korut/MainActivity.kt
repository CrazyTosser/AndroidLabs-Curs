package com.zhmyr.korut

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker

class MainActivity : AppCompatActivity() {
    lateinit var tv: TextView
    lateinit var sc: CoroutineScope

    var sec = 0

    override fun onPause() {
        super.onPause()
        sc.cancel()
        Log.v("inf", "Coroutin canceled")
    }

    override fun onStart() {
        super.onStart()
        if (!sc.isActive) {
            Log.v("inf", "Coroutin restart")
            restart()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            sec = getInt("sec")
        }
        Log.v("SEC", "Restore $sec")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.v("SEC", "Save $sec")
        outState.run {
            putInt("sec", sec)
            val edit = getSharedPreferences("CorutinWatch", Context.MODE_PRIVATE).edit()
            edit.putInt("sec", getInt("sec"))
            edit.commit()
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sec = getSharedPreferences("CorutinWatch", Context.MODE_PRIVATE).getInt("sec", 0)
        tv = findViewById(R.id.tsec)

        restart()
    }

    override fun onDestroy() {
        sc.cancel()
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun restart() {
        val tic = ticker(delayMillis = 1_000, initialDelayMillis = 1_000)
        sc = CoroutineScope(Dispatchers.Default + SupervisorJob())
        sc.launch(Dispatchers.IO) {
            Log.v("inf", "Coroutin start")
            while (true) {
                Log.v("inf", "Coroutin tic")
                tic.receive()
                sc.launch(Dispatchers.Main) { tv.text = "${++sec} seconds out" }
            }
        }
    }

}
