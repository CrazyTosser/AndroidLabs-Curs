package com.zhmyr.task3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var sc: CoroutineScope
    val url = "http://newrenomespb.ru/uploads/izd/1309.jpg"
    lateinit var res: Bitmap
    lateinit var pic:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sc = CoroutineScope(Dispatchers.Default + SupervisorJob())

        pic = findViewById(R.id.img)
        findViewById<Button>(R.id.button).setOnClickListener {
            sc.launch(Dispatchers.IO) {
                try {
                    val inp: InputStream = URL(url).openStream()
                    res = BitmapFactory.decodeStream(inp)
                    sc.launch(Dispatchers.Main) { pic.setImageBitmap(res) }
                } catch (ex: Exception){
                    Log.e("Image error", ex.message)
                    ex.printStackTrace()
                }
            }
        }
    }
}
