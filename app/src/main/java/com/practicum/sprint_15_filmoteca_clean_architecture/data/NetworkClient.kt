package com.practicum.sprint_15_filmoteca_clean_architecture.data

import com.practicum.sprint_15_filmoteca_clean_architecture.data.dto.Response

interface NetworkClient {
    fun doRequest (dto : Any) : Response
}