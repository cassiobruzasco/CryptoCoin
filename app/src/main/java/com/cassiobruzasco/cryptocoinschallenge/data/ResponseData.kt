package com.cassiobruzasco.cryptocoinschallenge.data

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response

sealed class ResponseData<out T : Any> {
    data object Empty : ResponseData<Nothing>()
    data class Success<out T : Any>(val data: T) : ResponseData<T>()
    data class Error(val message: String?) : ResponseData<Nothing>()
}

suspend fun <T : Any> mapApiResponse(
    execute: suspend () -> Response<T>
): ResponseData<T> {
    return try {
        val response = execute()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ResponseData.Success(body)
            } else {
                ResponseData.Empty
            }
        } else if (response.code() == 401) {
            ResponseData.Error(message = "Unauthorized")
        } else {
            val errorResponse = response.errorBody()?.string()?.takeIf { it.isNotEmpty() }
            ResponseData.Error(message = errorResponse)
        }
    } catch (e: HttpException) {
        ResponseData.Error(message = e.message())
    } catch (e: Throwable) {
        Log.e(ResponseData::class.java.simpleName, "Exception: ${e.message}", e)
        ResponseData.Error(message = e.message)
    }
}