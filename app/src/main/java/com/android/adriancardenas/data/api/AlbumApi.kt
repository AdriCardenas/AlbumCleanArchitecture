package com.android.adriancardenas.data.api

import com.android.adriancardenas.data.datasource.AlbumDatasource
import com.android.adriancardenas.domain.model.AlbumEntity
import com.android.adriancardenas.domain.model.PhotoEntity
import retrofit2.Response

class AlbumApi(private val api: AlbumApiService) : AlbumDatasource {
    override suspend fun getAlbums(userId: String): Response<List<AlbumEntity>> =
        api.getAlbums(userId)

    override suspend fun getPhotos(albumId: Int): Response<List<PhotoEntity>> =
        api.getPhotos(albumId)

    override suspend fun updateTitleAlbum(albumId: Int, title: String): Response<AlbumEntity> =
        api.updateAlbumTitle(albumId, title)

    override suspend fun updateAlbum(
        albumId: Int,
        albumEntity: AlbumEntity
    ): Response<AlbumEntity> = api.updateAlbum(albumId, albumEntity)

    override suspend fun createAlbum(albumEntity: AlbumEntity): Response<AlbumEntity> =
        api.createAlbum(albumEntity)

    override suspend fun deleteAlbum(id: Int): Response<Any> =
        api.deleteAlbum(id)
}