package com.jgarnier.menuapplication.data

/**
 * Utility class encapsulating a state with a potential result
 */
sealed class Result<T>(val data: T?) {
    class Loading<T> : Result<T>(null)
    class Empty<T> : Result<T>(null)
    class Success<T>(data: T) : Result<T>(data)
    data class Error<T>(val reason: String?) : Result<T>(null)
}