package com.alkemy.ongandroid.core

sealed class Response<out T>{

    class Loading<out T>: Response<T>()
    data class Success<out T>(val metaData: T) : Response<T>()
    data class Failure<out T>(val throwable: Throwable): Response<T>()

}
