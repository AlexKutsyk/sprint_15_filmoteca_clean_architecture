package com.practicum.sprint_15_filmoteca_clean_architecture.domain.impl

import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesInteractor
import com.practicum.sprint_15_filmoteca_clean_architecture.domain.api.MoviesRepository
import com.practicum.sprint_15_filmoteca_clean_architecture.util.Resource

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    override fun searchMovies(exception: String, consumer: MoviesInteractor.MoviesConsumer) {
        val thread = Thread {
            when (val resource = repository.searchMovies(exception)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
        thread.start()
    }
}