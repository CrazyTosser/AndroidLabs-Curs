package com.zhmyr.async

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var seconds: Stopwatch
    private lateinit var text_seconds_elapsed: TextView
    private var secondsElapsed = 0

    override fun onPause() {
        seconds.cancel(true)
        prefs.edit().putInt("sec", secondsElapsed).apply()
        super.onPause()
    }

    override fun onResume() {
        if (seconds.isCancelled) {
            seconds = Stopwatch(this)
            seconds.execute()
        }
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        if (prefs.contains("sec")) {
            secondsElapsed = prefs.getInt("sec", 0)
            text_seconds_elapsed.text = "$secondsElapsed seconds out"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("AsyncWatch", Context.MODE_PRIVATE)

        text_seconds_elapsed = findViewById(R.id.tsec)

        seconds = Stopwatch(this)
        seconds.execute()

    }

    companion object {
        private class Stopwatch internal constructor(private val mainActivity: MainActivity) :
            AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg params: Void?): Void? {
                while (!isCancelled) {
                    Log.v("async", "tic ${mainActivity.secondsElapsed}")
                    TimeUnit.SECONDS.sleep(1)
                    publishProgress()
                }
                return null
            }

            override fun onProgressUpdate(vararg values: Void?) {
                super.onProgressUpdate(*values)
                mainActivity.text_seconds_elapsed.text =
                    "${++mainActivity.secondsElapsed} seconds out"

            }
        }
    }
}