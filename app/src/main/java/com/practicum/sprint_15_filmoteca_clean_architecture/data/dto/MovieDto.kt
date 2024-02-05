package com.practicum.sprint_15_filmoteca_clean_architecture.data.dto

data class MovieDto(
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String
)