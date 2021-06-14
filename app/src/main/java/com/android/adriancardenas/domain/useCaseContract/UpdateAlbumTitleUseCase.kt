package com.android.adriancardenas.domain.useCaseContract

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel

interface UpdateAlbumTitleUseCase {
    suspend fun updateAlbumTitle(albumId: Int, title: String): Either<ErrorModel, Album>
}