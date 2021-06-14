package com.android.adriancardenas.data.datasource

import com.android.adriancardenas.domain.model.AlbumEntity
import com.android.adriancardenas.domain.model.PhotoEntity
import retrofit2.Response

interface AlbumDatasource {
    suspend fun getAlbums(userId:String): Response<List<AlbumEntity>>
    suspend fun getPhotos(albumId:Int): Response<List<PhotoEntity>>
    suspend fun updateTitleAlbum(albumId:Int, title:String): Response<AlbumEntity>
    suspend fun updateAlbum(albumId:Int, albumEntity:AlbumEntity): Response<AlbumEntity>
    suspend fun createAlbum(albumEntity: AlbumEntity): Response<AlbumEntity>
    suspend fun deleteAlbum(id:Int): Response<Any>
}