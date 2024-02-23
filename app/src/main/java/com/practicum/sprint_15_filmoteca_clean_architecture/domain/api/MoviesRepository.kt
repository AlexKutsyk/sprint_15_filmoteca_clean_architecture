package com.practicum.sprint_15_filmoteca_clean_architecture.domain.api

import com.practicum.sprint_15_filmoteca_clean_architecture.domain.models.Movie
import com.practicum.sprint_15_filmoteca_clean_architecture.util.Resource

interface MoviesRepository {
    fun searchMovies (expression: String) : Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}