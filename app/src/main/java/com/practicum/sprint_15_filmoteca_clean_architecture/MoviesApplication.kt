package com.practicum.sprint_15_filmoteca_clean_architecture

import android.app.Application
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies.MoviesSearchViewModel

class MoviesApplication: Application() {
    var moviesSearchPresenter: MoviesSearchViewModel? = null
}