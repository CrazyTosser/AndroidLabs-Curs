package com.zhmyr.task4

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    val url = "http://newrenomespb.ru/uploads/izd/1609.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            Glide.with(this).load(url).error(ColorDrawable(Color.RED)).into(findViewById(R.id.img))
        }
    }
}
