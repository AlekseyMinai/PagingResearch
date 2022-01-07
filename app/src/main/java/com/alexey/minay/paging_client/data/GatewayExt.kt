package com.alexey.minay.paging_client.data

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend inline fun <reified T> wrapRequest(crossinline request: suspend () -> HttpResponse) =
    withContext(Dispatchers.IO) {
        // TODO: 07.01.2022 handle internet connection error

        try {
            val response = request()
            when (response.status) {
                HttpStatusCode.OK -> Result.Success(response.receive<T>())
                // TODO: 07.01.2022 handle http statuses
                else -> Result.Error(Result.Error.Type.UnknownError)
            }
        } catch (ex: Exception) {
            Result.Error(Result.Error.Type.UnknownError)
        }
    }

fun <T, D> Result<T>.map(mapper: T.() -> D) =
    when (this) {
        is Result.Success -> Result.Success(data.mapper())
        is Result.Error -> this
    }