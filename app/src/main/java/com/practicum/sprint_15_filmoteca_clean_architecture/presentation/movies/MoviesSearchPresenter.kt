package com.practicum.sprint_15_filmoteca_clean_architecture.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.practicum.sprint_15_filmoteca_clean_architecture.util.Creator
import com.practicum.sprint_15_filmoteca_clean_architecture.R
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesInteractor
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.models.Movie
import com.practicum.sprint_15_filmoteca_clean_architecture.ui.movies.models.MoviesState
import moxy.MvpPresenter

class MoviesSearchPresenter(
    private val context: Context,
) : MvpPresenter<MoviesView>() {

    private var lastSearchText: String? = null

    private val moviesInteractor = Creator.provideMoviesInteractor(context)

    private val movies = ArrayList<Movie>()

    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            renderState(MoviesState(movies = movies, isLoading = true, message = null))

            moviesInteractor.searchMovies(
                newSearchText,
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                        handler.post {
                            if (foundMovies != null) {
                                movies.clear()
                                movies.addAll(foundMovies)
                            }

                            when {

                                errorMessage != null -> {
                                    renderState(MoviesState(
                                        movies = emptyList(),
                                        isLoading = false,
                                        message = context.getString(R.string.something_went_wrong)
                                    ))
                                    viewState.showToast(errorMessage)
                                }

                                movies.isEmpty() -> {
                                    renderState(MoviesState(
                                        movies = emptyList(),
                                        isLoading = false,
                                        message = context.getString(R.string.nothing_found)
                                    ))
                                }

                                else -> {
                                    renderState(MoviesState(
                                        movies = movies,
                                        isLoading = false,
                                        message = null
                                    ))
                                }
                            }
                        }
                    }
                })
        }
    }

    fun renderState(state: MoviesState) {
        viewState.render(state)
    }

    override fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }

        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}