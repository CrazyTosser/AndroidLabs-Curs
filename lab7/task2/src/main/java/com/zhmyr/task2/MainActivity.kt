package com.zhmyr.task2

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

const val ACTION_LOAD = "com.zhmyr.lab7.action.load"
const val ACTION_SAVE = "com.zhmyr.lab7.action.save"

class MainActivity : AppCompatActivity() {

    private lateinit var img: ImageView
    private lateinit var tPath: TextView
    val lBroad = LoadReciever()
    val sBroad = SaveReciever()
    val thisActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img = findViewById(R.id.imageView)
        tPath = findViewById(R.id.tPath)

        findViewById<Button>(R.id.button).setOnClickListener {
            tPath.text = ""
            img.setImageResource(0)
        }

        registerReceiver(lBroad, IntentFilter(ACTION_LOAD)
            .apply { addCategory(Intent.CATEGORY_DEFAULT) })


        registerReceiver(sBroad, IntentFilter(ACTION_SAVE)
            .apply { addCategory(Intent.CATEGORY_DEFAULT) })

    }

    override fun isDestroyed(): Boolean {
        unregisterReceiver(sBroad)
        unregisterReceiver(lBroad)
        return super.isDestroyed()
    }


    inner class LoadReciever : BroadcastReceiver() {
        override fun onReceive(cnt: Context?, intent: Intent?) {
            Toast.makeText(applicationContext, "Recieve load answer", Toast.LENGTH_LONG).show()
            val bit = intent?.getByteArrayExtra("bitmap") ?: ByteArray(0)
            img.setImageBitmap(BitmapFactory.decodeByteArray(bit, 0, bit.size))
        }
    }

    inner class SaveReciever : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(thisActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    10)
            Toast.makeText(applicationContext, "Recieve save answer", Toast.LENGTH_LONG).show()
            Log.v("Save", intent?.getStringExtra("path"))
            val imgFile = File(intent?.getStringExtra("path") ?: "")
            img.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
            tPath.text = imgFile.absolutePath
        }

    }
}
