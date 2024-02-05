package com.practicum.sprint_15_filmoteca_clean_architecture.util


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T?=null): Resource<T>(data, message)
}