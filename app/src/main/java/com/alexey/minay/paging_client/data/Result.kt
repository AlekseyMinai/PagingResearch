package com.alexey.minay.paging_client.data

sealed interface Result<out T> {
    class Success<T>(val data: T) : Result<T>
    class Error(val type: Type) : Result<Nothing> {
        sealed interface Type {
            object UnknownError : Type
        }
    }
}