package com.zhmyr.threads

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    lateinit var tv: TextView
    var pause = false
    var stop = false
    var thread:Thread = Thread(
            Runnable {
                while (true) {
                    Log.v("thread", "$sec")
                    if (pause) continue
                    if (stop) break
                    sec++
                    hn.sendEmptyMessage(0)
                    sleep(1000)
                }
            }
        )

    var sec = 0

    val hn = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            tv.text = "$sec seconds out"
        }
    }

    override fun onPause() {
        super.onPause()
        pause = true
    }

    override fun onStart() {
        super.onStart()
        pause = false
    }

    override fun onDestroy() {
        stop = true
        super.onDestroy()
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
            val edit = getSharedPreferences("WatchThread", Context.MODE_PRIVATE).edit()
            edit.putInt("sec", getInt("sec"))
            edit.commit()
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.sec)

        sec = getSharedPreferences("WatchThread", Context.MODE_PRIVATE).getInt("sec", 0)

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
            finish()
        }

        thread.isDaemon = true
        thread.start()

    }
}
