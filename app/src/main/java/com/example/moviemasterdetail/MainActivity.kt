package com.example.moviemasterdetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemasterdetail.adapter.MovieAdapter
import com.example.moviemasterdetail.db.MovieDbHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var db: MovieDbHelper
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val rvMovies = findViewById<RecyclerView>(R.id.rvMovies)
        val fabAdd   = findViewById<FloatingActionButton>(R.id.fabAdd)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        db = MovieDbHelper(this)
        adapter = MovieAdapter(db.getAllMovies()) { movie ->
            startActivity(
                Intent(this, DetailActivity::class.java)
                    .putExtra("MOVIE_ID", movie.id)
            )
        }

        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = adapter

        fabAdd.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        adapter.update(db.getAllMovies())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, QueryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}