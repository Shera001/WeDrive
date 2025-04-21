package com.example.wedrive.core.network.utils

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Result<T> {
    return try {
        Result.success(apiCall())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}