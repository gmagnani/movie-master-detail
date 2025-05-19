package com.example.moviemasterdetail.model

data class Movie(
    var id: Long = 0,
    var title: String,
    var genre: String,
    var year: Int,
    var director: String
)