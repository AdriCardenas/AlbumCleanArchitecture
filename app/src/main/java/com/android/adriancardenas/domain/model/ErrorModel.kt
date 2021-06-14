package com.android.adriancardenas.domain.model

sealed class ErrorModel {

    var throwable:Throwable? = null

    fun throwable(throwable: Throwable) = apply { this.throwable = throwable }

    object Network : ErrorModel()

    object Api : ErrorModel()

    object NotFound : ErrorModel()

    object AccessDenied : ErrorModel()

    object ServiceUnavailable : ErrorModel()

    object Unknown : ErrorModel()
}