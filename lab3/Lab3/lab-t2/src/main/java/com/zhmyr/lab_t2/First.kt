package com.zhmyr.lab_t2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_first.*

class First : AppCompatActivity() {
    val TAG = "STATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        intent = Intent()
        setResult(Activity.RESULT_OK, intent)

        //Action Bar
        //setSupportActionBar(toolbar_second)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.first)
        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout_first,
            toolbar_first,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}
        //Drawer
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout_first.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // Set navigation view navigation item selected listener
        nav_view_first.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_item -> startActivity(Intent(this, About::class.java))

            }
            // Close the drawer
            drawer_layout_first.closeDrawer(GravityCompat.START)
            true
        }

        val btnToSecond = findViewById<Button>(R.id.but_first)

        btnToSecond.setOnClickListener {
            startActivity(Intent(this, Second::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            when  (requestCode){
                1 -> log("Launch of Second was failed")
                else -> log("Launch of ")}
        }
    }

    private fun log(msg: String) {
        Log.e(TAG, msg)
    }
}
