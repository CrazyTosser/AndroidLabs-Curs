package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var stop = false
    var pause = false

    var backgroundThread = Thread {
        while (true) {
            if (pause) continue
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
            if (stop) return@Thread
        }
    }

    override fun onDestroy() {
        stop = true
        super.onDestroy()
    }

    override fun onResume() {
        pause = false
        super.onResume()
    }

    override fun onStop() {
        pause = true
        super.onStop()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.run {
            secondsElapsed = getInt("sec")
        }
        Log.v("SEC", "Restore " + secondsElapsed.toString())
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.v("SEC", "Save " + secondsElapsed.toString())
        outState.run {
            putInt("sec", secondsElapsed)
            val edit = getSharedPreferences("StopWatch", Context.MODE_PRIVATE).edit()
            edit.putInt("sec", getInt("sec"))
            edit.commit()
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getSharedPreferences("StopWatch", Context.MODE_PRIVATE) != null)
            secondsElapsed = getSharedPreferences("StopWatch", Context.MODE_PRIVATE).getInt("sec", 0)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null){
            with(savedInstanceState) {
                secondsElapsed = getInt("sec")
            }
        }
        Log.v("SEC", "Start")
        backgroundThread.start()
    }
}
