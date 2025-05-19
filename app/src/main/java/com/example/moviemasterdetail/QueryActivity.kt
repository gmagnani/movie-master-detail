package com.example.moviemasterdetail

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemasterdetail.R
import com.example.moviemasterdetail.db.MovieDbHelper

class QueryActivity : AppCompatActivity() {

    private lateinit var db: MovieDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)


        val etSearch  = findViewById<EditText>(R.id.etSearch)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val tvResults = findViewById<TextView>(R.id.tvResults)

        db = MovieDbHelper(this)

        btnSearch.setOnClickListener {
            val q       = etSearch.text.toString()
            val results = db.findByTitle(q)
            tvResults.text = if (results.isEmpty()) {
                "Nenhum filme encontrado"
            } else {
                results.joinToString("\n") { "${it.title} (${it.year})" }
            }
        }
    }
}