package com.android.adriancardenas.presentation.usecases

import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import com.android.adriancardenas.domain.useCaseContract.DeleteAlbumUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteAlbumUseCaseImpl(
    private val albumRepository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher
) : DeleteAlbumUseCase {
    override suspend fun deleteAlbum(id: Int): Either<ErrorModel, Any> =
        withContext(dispatcher) {
            albumRepository.deleteAlbum(id)
        }
}