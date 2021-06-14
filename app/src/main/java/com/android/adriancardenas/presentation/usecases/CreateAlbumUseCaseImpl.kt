package com.android.adriancardenas.presentation.usecases

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import com.android.adriancardenas.domain.useCaseContract.CreateAlbumUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CreateAlbumUseCaseImpl(
    private val albumRepository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher
) : CreateAlbumUseCase {
    override suspend fun createAlbum(album: Album): Either<ErrorModel, Album> =
        withContext(dispatcher) {
            albumRepository.createAlbum(album)
        }
}