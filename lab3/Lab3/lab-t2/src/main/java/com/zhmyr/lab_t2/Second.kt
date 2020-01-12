package com.zhmyr.lab_t2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_second.*


class Second : AppCompatActivity() {

    val TAG = "STATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        intent = Intent()
        //Action Bar
        //setSupportActionBar(toolbar_second)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.second)
        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout_second,
            toolbar_second,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}
        //Drawer
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout_second.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // Set navigation view navigation item selected listener
        nav_view_second.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_item -> startActivity(Intent(this, About::class.java))
            }
            // Close the drawer
            drawer_layout_second.closeDrawer(GravityCompat.START)
            true
        }

        val btnToFirst = findViewById<Button>(R.id.but_second_1)
        val btnToThird = findViewById<Button>(R.id.but_second_3)

        btnToFirst.setOnClickListener {
            this.finish()
        }

        btnToThird.setOnClickListener {
            Third.finishWhenExist()

            startActivityForResult(Intent(this, Third::class.java), 12)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12 && data?.getBooleanExtra("exit", false)!!)
            this.finish()
    }

    private fun log(msg: String) {
        Log.e(TAG, msg)
    }
}