package com.android.adriancardenas.domain.useCaseContract

import com.android.adriancardenas.data.model.Photo
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel

interface GetPhotosByAlbumUseCase {
    suspend fun getPhotos(albumId: Int): Either<ErrorModel, List<Photo>>
}