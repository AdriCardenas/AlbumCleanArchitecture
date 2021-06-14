package com.android.adriancardenas.data.repository

import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <R> request(
        call: suspend () -> Response<R>
    ): Either<ErrorModel, R> {
        return try {
            val response = call()
            when (response.isSuccessful) {
                true -> Either.Right(checkNotNull(response.body()))
                false -> Either.Left(ErrorModel.ServiceUnavailable)
            }
        } catch (exception: Throwable) {
            Either.Left(ErrorModel.Network.throwable(exception))
        }
    }

    suspend fun <T, R> request(
        call: suspend () -> Response<T>,
        transform: (T) -> R
    ): Either<ErrorModel, R> {
        return try {
            val response = call()
            when (response.isSuccessful) {
                true -> Either.Right(transform(checkNotNull(response.body())))
                false -> Either.Left(ErrorModel.ServiceUnavailable)
            }
        } catch (exception: Throwable) {
            Either.Left(ErrorModel.Network.throwable(exception))
        }
    }
}