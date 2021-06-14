package com.android.adriancardenas.domain.useCaseContract

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel

interface CreateAlbumUseCase {
    suspend fun createAlbum(album: Album): Either<ErrorModel, Album>
}