package com.android.adriancardenas.domain.useCaseContract

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel

interface FilterAlbumsUseCase {
    suspend fun filterByName(query: String, list: List<Album>): Either<ErrorModel, List<Album>>
}