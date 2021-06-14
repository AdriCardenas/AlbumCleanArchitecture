package com.android.adriancardenas.presentation.usecases

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import com.android.adriancardenas.domain.useCaseContract.UpdateAlbumUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateAlbumUseCaseImpl(
    private val albumRepository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher
) :UpdateAlbumUseCase {
    override suspend fun updateAlbum(albumId: Int, album: Album): Either<ErrorModel, Album> =
        withContext(dispatcher) {
            albumRepository.updateAlbum(albumId, album)
        }
}