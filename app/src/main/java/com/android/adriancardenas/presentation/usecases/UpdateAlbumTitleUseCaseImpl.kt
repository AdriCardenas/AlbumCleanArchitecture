package com.android.adriancardenas.presentation.usecases

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import com.android.adriancardenas.domain.useCaseContract.UpdateAlbumTitleUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateAlbumTitleUseCaseImpl(
    private val albumRepository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher
) : UpdateAlbumTitleUseCase {
    override suspend fun updateAlbumTitle(albumId: Int, title:String): Either<ErrorModel, Album> =
        withContext(dispatcher) {
            albumRepository.updateAlbumTitle(albumId, title)
        }
}