package com.practicum.sprint_15_filmoteca_clean_architecture.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.practicum.sprint_15_filmoteca_clean_architecture.data.MoviesRepositoryImpl
import com.practicum.sprint_15_filmoteca_clean_architecture.data.network.RetrofitNetworkClient
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesInteractor
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesRepository
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.impl.MoviesInteractorImpl
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies.MoviesSearchPresenter
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies.MoviesView
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.poster.PosterPresenter
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.poster.PosterView
import com.practicum.sprint_15_filmoteca_clean_architecture.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(context: Context) : MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context), context)
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(context: Context): MoviesSearchPresenter {
        return MoviesSearchPresenter(context)
    }

    fun providePosterPresenter(viewPoster: PosterView, urlPoster: String) : PosterPresenter {
        return PosterPresenter(viewPoster, urlPoster)
    }
}