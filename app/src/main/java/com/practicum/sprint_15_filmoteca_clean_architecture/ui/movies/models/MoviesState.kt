package com.practicum.sprint_15_filmoteca_clean_architecture.ui.movies.models

import com.practicum.sprint_15_filmoteca_clean_architecture.domain.models.Movie
import java.util.Objects

sealed interface MoviesState {

    data object Loading: MoviesState

    data class Content (val movies: List<Movie>) : MoviesState

    data class Error (val errorMessage: String) : MoviesState

    data class Empty (val message: String): MoviesState

}