package com.practicum.sprint_15_filmoteca_clean_architecture.data

import android.content.Context
import com.practicum.sprint_15_filmoteca_clean_architecture.R
import com.practicum.sprint_15_filmoteca_clean_architecture.data.dto.MoviesSearchRequest
import com.practicum.sprint_15_filmoteca_clean_architecture.data.dto.MoviesSearchResponse
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesRepository
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.models.Movie
import com.practicum.sprint_15_filmoteca_clean_architecture.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val context: Context,
    private val localStorage: LocalStorage
) :
    MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error(context.getString(R.string.check_connection))
            200 -> {
                val storage = localStorage.getSaveFavorites()

                if ((response as MoviesSearchResponse).results.isEmpty()) {
                    Resource.Error(context.getString(R.string.nothing_found))
                } else {
                    Resource.Success(response.results.map {
                        Movie(
                            it.id,
                            it.resultType,
                            it.image,
                            it.title,
                            it.description,
                            storage.contains(it.id)
                        )
                    })
                }
            }

            else -> Resource.Error(context.getString(R.string.servers_error))
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addFavorite(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFavorites(movie.id)
    }
}