package com.android.adriancardenas.presentation.usecases

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import com.android.adriancardenas.domain.useCaseContract.GetAlbumsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetAlbumsUserCaseImpl(
    private val albumRepository: AlbumRepository,
    private val dispatcher: CoroutineDispatcher
) : GetAlbumsUseCase {

    override suspend fun getAlbums(userId: String): Either<ErrorModel, List<Album>> =
        withContext(dispatcher) {
            albumRepository.getAlbums(userId)
        }
}