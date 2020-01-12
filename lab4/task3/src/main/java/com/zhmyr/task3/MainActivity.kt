package com.zhmyr.task3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhmyr.biblib.BibDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: BibDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.my_recycler_view)

        database = BibDatabase(
            this::class.java.getResourceAsStream("/res/raw/references.bib")!!.reader()
        )
        database.cfg.shuffle = true
        database.cfg.strict = true
        val entriesNumber = database.entriesSize

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BibLibAdapter(entriesNumber, database)
    }
}