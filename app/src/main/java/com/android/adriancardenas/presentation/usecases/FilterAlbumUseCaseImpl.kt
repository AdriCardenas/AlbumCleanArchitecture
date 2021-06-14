package com.android.adriancardenas.presentation.usecases

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.useCaseContract.FilterAlbumsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FilterAlbumUseCaseImpl(
    private val dispatcher: CoroutineDispatcher
) : FilterAlbumsUseCase {
    override suspend fun filterByName(
        query: String,
        list: List<Album>
    ): Either<ErrorModel, List<Album>> = withContext(dispatcher) {
        Either.Right(list.filter { it.title.contains(query) })
    }
}