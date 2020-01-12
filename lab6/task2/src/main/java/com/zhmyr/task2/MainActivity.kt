package com.zhmyr.task2

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.InputStream
import java.lang.Exception
import java.net.URL


class MainActivity : AppCompatActivity() {

    @SuppressLint("StaticFieldLeak")
    inner class imgWorker(val pic:ImageView) : AsyncTask<String, Void, Bitmap?>(){
        override fun doInBackground(vararg p0: String?): Bitmap? {
            val url = p0[0]
            var res: Bitmap? = null
            try {
                val inp: InputStream = URL(url).openStream()
                res = BitmapFactory.decodeStream(inp)
            } catch (ex:Exception){
                CoroutineScope(Dispatchers.Default + SupervisorJob()).launch(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error of image loading", Toast.LENGTH_LONG).show()
                }
                Log.e("Image error", ex.message)
                ex.printStackTrace()
            }
            return res
        }

        override fun onPostExecute(result: Bitmap?) {
            pic.setImageBitmap(result)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            imgWorker(findViewById(R.id.img)).execute("http://newrenomespb.ru/uploads/izd/1509.jpg")
        }
    }
}
