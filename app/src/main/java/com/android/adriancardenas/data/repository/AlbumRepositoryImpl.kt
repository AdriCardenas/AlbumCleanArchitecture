package com.android.adriancardenas.data.repository

import com.android.adriancardenas.data.datasource.AlbumDatasource
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.data.model.Photo
import com.android.adriancardenas.domain.model.Either
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository

class AlbumRepositoryImpl(
    private val datasource: AlbumDatasource
) : AlbumRepository, BaseRepository() {

    override suspend fun getAlbums(userId: String): Either<ErrorModel, List<Album>> =
        request(
            call = { datasource.getAlbums(userId) },
            transform = { it.map { albumEntity -> albumEntity.transformToAlbum() } }
        )

    override suspend fun getPhotos(albumId: Int): Either<ErrorModel, List<Photo>> =
        request(
            call = { datasource.getPhotos(albumId) },
            transform = { it.map { photoEntity -> photoEntity.transformToPhoto() } }
        )

    override suspend fun updateAlbumTitle(albumId: Int, title: String): Either<ErrorModel, Album> =
        request(
            call = { datasource.updateTitleAlbum(albumId, title) },
            transform = { it.transformToAlbum() }
        )

    override suspend fun updateAlbum(albumId: Int, album: Album): Either<ErrorModel, Album> =
        request(
            call = { datasource.updateAlbum(albumId, album.albumToAlbumEntity()) },
            transform = { it.transformToAlbum() }
        )

    override suspend fun createAlbum(album: Album): Either<ErrorModel, Album> =
        request(
            call = { datasource.createAlbum(album.albumToAlbumEntity()) },
            transform = { it.transformToAlbum() }
        )

    override suspend fun deleteAlbum(id:Int): Either<ErrorModel, Any> = request(
        call = { datasource.deleteAlbum(id) }
    )
}