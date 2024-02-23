package com.practicum.sprint_15_filmoteca_clean_architecture.domain.api

import com.practicum.sprint_15_filmoteca_clean_architecture.domain.models.Movie
import java.lang.Exception

interface MoviesInteractor {
    fun searchMovies (exception: String, consumer: MoviesConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}