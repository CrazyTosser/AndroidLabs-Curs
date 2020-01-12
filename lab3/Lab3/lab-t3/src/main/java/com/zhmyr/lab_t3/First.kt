package com.zhmyr.lab_t3

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

        val btnToSecond = findViewById<Button>(R.id.but_first_2)
        val btnToThird = findViewById<Button>(R.id.but_first_3)

        btnToSecond.setOnClickListener {
            startActivity(Intent(this, Second::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }

        btnToThird.setOnClickListener {
            startActivity(Intent(this, Second::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        }
    }

    private fun log(msg: String) {
        Log.e(TAG, msg)
    }
}
