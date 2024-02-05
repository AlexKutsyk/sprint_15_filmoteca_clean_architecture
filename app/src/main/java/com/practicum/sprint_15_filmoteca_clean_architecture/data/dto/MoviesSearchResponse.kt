package com.practicum.sprint_15_filmoteca_clean_architecture.data.dto


data class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>,
) : Response()