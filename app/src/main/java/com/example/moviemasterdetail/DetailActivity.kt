package com.example.moviemasterdetail

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemasterdetail.db.MovieDbHelper
import com.example.moviemasterdetail.model.Movie

class DetailActivity : AppCompatActivity() {

    private lateinit var db: MovieDbHelper
    private var movieId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val etTitle    = findViewById<EditText>(R.id.etTitle)
        val etGenre    = findViewById<EditText>(R.id.etGenre)
        val etYear     = findViewById<EditText>(R.id.etYear)
        val etDirector = findViewById<EditText>(R.id.etDirector)
        val btnSave    = findViewById<Button>(R.id.btnSave)
        val btnDelete  = findViewById<Button>(R.id.btnDelete)

        db = MovieDbHelper(this)
        movieId = intent.getLongExtra("MOVIE_ID", 0L)

        if (movieId != 0L) {

            val m = db.getAllMovies().first { it.id == movieId }
            etTitle.setText(m.title)
            etGenre.setText(m.genre)
            etYear.setText(m.year.toString())
            etDirector.setText(m.director)
        }

        btnSave.setOnClickListener {
            val title    = etTitle.text.toString()
            val genre    = etGenre.text.toString()
            val year     = etYear.text.toString().toIntOrNull() ?: 0
            val director = etDirector.text.toString()

            val movie = Movie(
                id       = movieId,
                title    = title,
                genre    = genre,
                year     = year,
                director = director
            )
            if (movieId == 0L) db.addMovie(movie) else db.updateMovie(movie)
            finish()
        }

        btnDelete.setOnClickListener {
            if (movieId != 0L) db.deleteMovie(movieId)
            finish()
        }
    }
}