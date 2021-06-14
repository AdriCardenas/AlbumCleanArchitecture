package com.android.adriancardenas.domain.useCaseContract

import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel

interface DeleteAlbumUseCase {
    suspend fun deleteAlbum(id:Int): Either<ErrorModel, Any>
}