package com.zhmyr.lab7

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.*
import java.net.URL


// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
const val ACTION_LOAD = "com.zhmyr.lab7.action.load"
const val ACTION_SAVE = "com.zhmyr.lab7.action.save"

const val MSG_LOAD = 467
const val MSG_LOAD_RESP = 477
const val MSG_SAVE = 567
const val MSG_SAVE_RESP = 577

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "com.zhmyr.lab7.extra.url"
private const val EXTRA_PARAM2 = "com.zhmyr.lab7.extra.fileName"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class ImgService : IntentService("ImgService") {

    val msgr = Messenger(@SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_LOAD -> {
                    Toast.makeText(
                        applicationContext,
                        "Load image by binding",
                        Toast.LENGTH_SHORT
                    ).show()
                    val res = ByteArrayOutputStream()
                    val url = msg.data.getString("url") ?: ""
                    if (!url.isEmpty()) {
                        var bitmap: Bitmap? = null
                        runBlocking(Dispatchers.IO) { bitmap = async { getBitmap(url) }.await() }
                        if (bitmap != null)
                            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 50, res)
                        msg.replyTo.send(Message.obtain(null, MSG_LOAD_RESP).apply {
                            data = Bundle().apply {
                                putByteArray("img", res.toByteArray())
                            }
                        })
                    }
                }
                MSG_SAVE -> {
                    Toast.makeText(
                        applicationContext,
                        "Load image for saving by binding",
                        Toast.LENGTH_SHORT
                    ).show()
                    val fname = msg.data.getString("fname") ?: ""
                    val res = (msg.data.getString("url")?.let {
                        runBlocking(Dispatchers.IO) {  async { getBitmap(it) }.await() }
                    })
                    FileOutputStream(fname).use {
                        res?.compress(Bitmap.CompressFormat.JPEG, 50, it)
                    }
                    msg.replyTo.send(Message.obtain(null, MSG_SAVE_RESP).apply {
                        data.putString("fname", fname)
                    })

                }
                else -> super.handleMessage(msg)
            }
        }
    })

    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(applicationContext, "binding service", Toast.LENGTH_LONG).show()
        return msgr.binder
    }

    override fun onHandleIntent(intent: Intent?) {
        val logFile = File("/data/data/com.zhmyr.lab7/cache/log")
            .apply { if (!exists()) createNewFile() }
        when (intent?.action) {
            ACTION_LOAD -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                FileWriter(logFile, true).apply {
                    write("${intent.`package`} $param1\n")
                    flush()
                }.close()
                handleLoad(param1)
            }
            ACTION_SAVE -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleSave(param1, param2)
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleLoad(url: String) {
        var res: Bitmap? = getBitmap(url)
        val bite = ByteArrayOutputStream()
        if (res != null) {
            res.compress(Bitmap.CompressFormat.JPEG, 50, bite)
            val intent = Intent().apply {
                setAction(ACTION_LOAD)
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("bitmap", bite.toByteArray())
            }
            sendBroadcast(intent)
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleSave(url: String, fname: String) {
        var res: Bitmap? = getBitmap(url)
        if (res != null) {
            FileOutputStream(fname).use {
                res.compress(Bitmap.CompressFormat.JPEG, 50, it)
            }
            val intent = Intent().setAction(ACTION_SAVE).addCategory(Intent.CATEGORY_DEFAULT)
                .putExtra("path", fname)
            sendBroadcast(intent)
        }
    }

    private fun getBitmap(url: String): Bitmap? {
        var res :Bitmap? = null
        try {
            val inp: InputStream = URL(url).openStream()
            res = BitmapFactory.decodeStream(inp)
            inp.close()
        } catch (ex:FileNotFoundException) {}
        return res
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionLoad(context: Context, url: String) {
            val intent = Intent(context, ImgService::class.java).apply {
                action = ACTION_LOAD
                putExtra(EXTRA_PARAM1, url)
            }
            context.startService(intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionSave(context: Context, url: String, fname: String) {
            val intent = Intent(context, ImgService::class.java).apply {
                action = ACTION_SAVE
                putExtra(EXTRA_PARAM1, url)
                putExtra(EXTRA_PARAM2, context.getExternalFilesDir("Lab")?.absolutePath + "/" + fname)
            }
            context.startService(intent)
        }
    }
}
