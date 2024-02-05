package com.practicum.sprint_15_filmoteca_clean_architecture.ui.movies.models

import com.practicum.sprint_15_filmoteca_clean_architecture.domain.models.Movie

data class MoviesState (
    val movies: List<Movie>,
    val isLoading: Boolean,
    val message: String?
    ){
}