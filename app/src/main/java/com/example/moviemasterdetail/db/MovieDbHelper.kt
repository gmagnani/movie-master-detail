package com.example.moviemasterdetail.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviemasterdetail.model.Movie

class MovieDbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "movies.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "movies"
        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_GENRE = "genre"
        const val COL_YEAR = "year"
        const val COL_DIRECTOR = "director"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
              $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
              $COL_TITLE TEXT NOT NULL,
              $COL_GENRE TEXT,
              $COL_YEAR INTEGER,
              $COL_DIRECTOR TEXT
            );
        """.trimIndent()
        db.execSQL(createTable)

        // Pré-seeding com 2 filmes
        insert(db, Movie(title="The Matrix", genre="Sci-Fi", year=1999, director="Wachowskis"))
        insert(db, Movie(title="Inception", genre="Sci-Fi", year=2010, director="Christopher Nolan"))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insert(db: SQLiteDatabase, movie: Movie) {
        val cv = ContentValues().apply {
            put(COL_TITLE, movie.title)
            put(COL_GENRE, movie.genre)
            put(COL_YEAR, movie.year)
            put(COL_DIRECTOR, movie.director)
        }
        db.insert(TABLE_NAME, null, cv)
    }

    // CRUD: Create
    fun addMovie(movie: Movie): Long {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL_TITLE, movie.title)
            put(COL_GENRE, movie.genre)
            put(COL_YEAR, movie.year)
            put(COL_DIRECTOR, movie.director)
        }
        return db.insert(TABLE_NAME, null, cv)
    }

    // Read all
    fun getAllMovies(): List<Movie> {
        val list = mutableListOf<Movie>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COL_TITLE ASC")
        with(cursor) {
            while (moveToNext()) {
                list += Movie(
                    id = getLong(getColumnIndexOrThrow(COL_ID)),
                    title = getString(getColumnIndexOrThrow(COL_TITLE)),
                    genre = getString(getColumnIndexOrThrow(COL_GENRE)),
                    year = getInt(getColumnIndexOrThrow(COL_YEAR)),
                    director = getString(getColumnIndexOrThrow(COL_DIRECTOR))
                )
            }
            close()
        }
        return list
    }

    // Read by name (LIKE)
    fun findByTitle(query: String): List<Movie> {
        val list = mutableListOf<Movie>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COL_TITLE LIKE ?",
            arrayOf("%$query%"),
            null, null,
            "$COL_TITLE ASC"
        )
        with(cursor) {
            while (moveToNext()) {
                list += Movie(
                    id = getLong(getColumnIndexOrThrow(COL_ID)),
                    title = getString(getColumnIndexOrThrow(COL_TITLE)),
                    genre = getString(getColumnIndexOrThrow(COL_GENRE)),
                    year = getInt(getColumnIndexOrThrow(COL_YEAR)),
                    director = getString(getColumnIndexOrThrow(COL_DIRECTOR))
                )
            }
            close()
        }
        return list
    }

    // Update
    fun updateMovie(movie: Movie): Int {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL_TITLE, movie.title)
            put(COL_GENRE, movie.genre)
            put(COL_YEAR, movie.year)
            put(COL_DIRECTOR, movie.director)
        }
        return db.update(TABLE_NAME, cv, "$COL_ID = ?", arrayOf(movie.id.toString()))
    }

    // Delete
    fun deleteMovie(id: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
    }
}