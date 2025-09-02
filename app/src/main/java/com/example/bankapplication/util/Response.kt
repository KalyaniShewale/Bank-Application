package com.example.bankapplication.util

/**
 * Sealed class representing API operation results with success, error, and loading states.
 * Provides structured handling for network responses and asynchronous operation status.
 */

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}