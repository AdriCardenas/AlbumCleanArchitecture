package com.android.adriancardenas.presentation.usecases

import com.android.adriancardenas.data.model.Photo
import com.android.adriancardenas.data.repository.AlbumRepositoryImpl
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import com.android.adriancardenas.domain.useCaseContract.GetPhotosByAlbumUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetPhotosByAlbumUseCaseImpl(
    private val repository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher
) : GetPhotosByAlbumUseCase {
    override suspend fun getPhotos(albumId: Int): Either<ErrorModel, List<Photo>> =
        withContext(dispatcher){
            repository.getPhotos(albumId)
        }
}