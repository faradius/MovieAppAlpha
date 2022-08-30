package com.alex.movieapp.core

import java.lang.Exception

sealed class ResourceResult<out T> {
    class Loading<out T>: ResourceResult<T>()

    data class Success<out T>(val data:T): ResourceResult<T>()
    data class Failure(val exception: Exception): ResourceResult<Nothing>()
}