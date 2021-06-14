package com.android.adriancardenas.domain.repositoryContract

import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.data.model.Photo
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel

interface AlbumRepository {
    suspend fun getAlbums(userId: String): Either<ErrorModel, List<Album>>
    suspend fun getPhotos(albumId: Int): Either<ErrorModel, List<Photo>>
    suspend fun updateAlbumTitle(albumId: Int, title: String): Either<ErrorModel, Album>
    suspend fun updateAlbum(albumId: Int, album: Album): Either<ErrorModel, Album>
    suspend fun createAlbum(album: Album): Either<ErrorModel, Album>
    suspend fun deleteAlbum(id: Int): Either<ErrorModel, Any>
}