package com.practicum.sprint_15_filmoteca_clean_architecture.util

import android.app.Application
import android.content.Context
import com.practicum.sprint_15_filmoteca_clean_architecture.data.LocalStorage
import com.practicum.sprint_15_filmoteca_clean_architecture.data.MoviesRepositoryImpl
import com.practicum.sprint_15_filmoteca_clean_architecture.data.network.RetrofitNetworkClient
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesInteractor
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesRepository
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.impl.MoviesInteractorImpl
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies.MoviesSearchViewModel
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.poster.PosterPresenter
import com.practicum.sprint_15_filmoteca_clean_architecture.presentation.poster.PosterView

object Creator {
    private fun getMoviesRepository(context: Context) : MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context), context, LocalStorage(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(context: Application): MoviesSearchViewModel {
        return MoviesSearchViewModel(context)
    }

    fun providePosterPresenter(viewPoster: PosterView, urlPoster: String) : PosterPresenter {
        return PosterPresenter(viewPoster, urlPoster)
    }
}