package com.zhmyr.lab5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_third.*

class Third : AppCompatActivity() {

    val TAG = "STATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        //Action Bar
        //setSupportActionBar(toolbar_second)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.third)
        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout_third,
            toolbar_third,
            R.string.drawer_open,
            R.string.drawer_close
        ) {}
        //Drawer
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout_third.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // Set navigation view navigation item selected listener
        nav_view_third.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_item ->
                    startActivityForResult(Intent(this, About::class.java), 12)
            }
            // Close the drawer
            drawer_layout_third.closeDrawer(GravityCompat.START)
            true
        }

        val btnToFirst = findViewById<Button>(R.id.but_third_1)
        val btnToSecond = findViewById<Button>(R.id.but_third_2)

        btnToFirst.setOnClickListener {
            startActivity(Intent(this, First::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }

        btnToSecond.setOnClickListener {
            startActivity(Intent(this, Second::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
    }

    private fun log(msg: String) {
        Log.e(TAG, msg)
    }
}
