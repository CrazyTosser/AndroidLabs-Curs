package com.zhmyr.lab7

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var tPath: TextView
    lateinit var ePath: EditText
    lateinit var name: EditText
    lateinit var img: ImageView
    private var mMessenger: Messenger? = null
    private val actMsg = Messenger(RespHandler(this))
    private var isBound: Boolean = false
    //val lBroad = LoadReciever()
    //val sBroad = SaveReciever()
    @SuppressLint("HandlerLeak")
    inner class RespHandler(val cnt: Context?) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_LOAD_RESP -> {
                    val bit = msg.data.getByteArray("img") ?: ByteArray(0)
                    img.setImageBitmap(BitmapFactory.decodeByteArray(bit, 0, bit.size))
                }
                MSG_SAVE_RESP -> {
                    val imgFile = File(msg.data.getString("fname") ?: "")
                    if (imgFile.exists()) {
                        img.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
                        tPath.text = imgFile.absolutePath
                    }
                }
            }
        }
    }

    var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mMessenger = Messenger(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            mMessenger = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                10)

        tPath = findViewById(R.id.spath)
        ePath = findViewById(R.id.path)
        name = findViewById(R.id.name)
        img = findViewById(R.id.imageView)

        findViewById<Button>(R.id.ld).setOnClickListener {
            ImgService.startActionLoad(this, ePath.text.toString())
        }

        findViewById<Button>(R.id.sv).setOnClickListener {
            ImgService.startActionSave(this, ePath.text.toString(), name.text.toString())
        }

        findViewById<Button>(R.id.ldm).setOnClickListener {
            val msg = Message.obtain(null, MSG_LOAD)
            msg.data.putString("url", ePath.text.toString())
            msg.replyTo = actMsg
            try {
                mMessenger?.send(msg)
            } catch (ex : RemoteException) {
                Log.e("bind", ex.message)
            }
        }

        findViewById<Button>(R.id.svm).setOnClickListener {
            val msg = Message.obtain(null, MSG_SAVE)
            msg.data.apply {
                putString("url", ePath.text.toString())
                putString("fname", cacheDir.canonicalPath + "/" + name.text.toString())
            }
            msg.replyTo = actMsg
            try {
                mMessenger?.send(msg)
            } catch (ex : RemoteException) {
                Log.e("bind", ex.message)
            }
        }

       /* registerReceiver(lBroad, IntentFilter(ACTION_LOAD)
            .apply { addCategory(Intent.CATEGORY_DEFAULT) })*/
        /*registerReceiver(sBroad, IntentFilter(ACTION_SAVE)
            .apply { addCategory(Intent.CATEGORY_DEFAULT) })*/
    }

    override fun onStart() {
        super.onStart()
        if (!isBound)
            bindService(
                Intent(this, ImgService::class.java),
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        //unregisterReceiver(lBroad)
        //unregisterReceiver(sBroad)
        mMessenger = null
        isBound = false
    }

    /*inner class LoadReciever : BroadcastReceiver() {
        override fun onReceive(cnt: Context?, intent: Intent?) {
            val bit = intent?.getByteArrayExtra("bitmap") ?: ByteArray(0)
            img.setImageBitmap(BitmapFactory.decodeByteArray(bit, 0, bit.size))
        }
    }*//*

    inner class SaveReciever : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val imgFile = File(intent?.getStringExtra("path") ?: "")
            if (imgFile.exists()) {
                img.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
                tPath.text = imgFile.absolutePath
            }
        }

    }*/
}
