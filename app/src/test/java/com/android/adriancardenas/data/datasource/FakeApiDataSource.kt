package com.android.adriancardenas.data.datasource

import com.android.adriancardenas.data.datasource.AlbumDatasource
import com.android.adriancardenas.domain.model.AlbumEntity
import com.android.adriancardenas.domain.model.PhotoEntity
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import retrofit2.Response
import java.net.HttpURLConnection

class FakeApiDataSource: AlbumDatasource {
    override suspend fun getAlbums(userId: String): Response<List<AlbumEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPhotos(albumId: Int): Response<List<PhotoEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTitleAlbum(albumId: Int, title: String): Response<AlbumEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAlbum(
        albumId: Int,
        albumEntity: AlbumEntity
    ): Response<AlbumEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun createAlbum(albumEntity: AlbumEntity): Response<AlbumEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlbum(id: Int): Response<Any> {
        return if(id>0){
            Response.success(Any())
        }else{
            val responseBody = object:ResponseBody(){
                override fun contentLength(): Long {
                    TODO("Not yet implemented")
                }

                override fun contentType(): MediaType? {
                    TODO("Not yet implemented")
                }

                override fun source(): BufferedSource {
                    TODO("Not yet implemented")
                }

            }
            Response.error(HttpURLConnection.HTTP_NOT_FOUND, responseBody)
        }
    }
}