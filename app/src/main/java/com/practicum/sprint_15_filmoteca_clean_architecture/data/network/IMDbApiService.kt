package com.practicum.sprint_15_filmoteca_clean_architecture.data.network

import com.practicum.sprint_15_filmoteca_clean_architecture.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun getMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>
}