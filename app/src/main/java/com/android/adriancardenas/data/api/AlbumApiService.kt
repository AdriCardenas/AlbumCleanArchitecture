package com.android.adriancardenas.data.api

import com.android.adriancardenas.domain.model.AlbumEntity
import com.android.adriancardenas.domain.model.PhotoEntity
import retrofit2.Response
import retrofit2.http.*

interface AlbumApiService {
    @GET("/albums")
    suspend fun getAlbums(@Query("userId") userId: String): Response<List<AlbumEntity>>

    @GET("/albums/{id}/photos")
    suspend fun getPhotos(@Path("id") id: Int): Response<List<PhotoEntity>>

    @POST("/albums")
    suspend fun createAlbum(@Body albumEntity: AlbumEntity): Response<AlbumEntity>

    @DELETE("/albums/{id}")
    suspend fun deleteAlbum(
        @Path("id") id: Int
    ): Response<Any>

    @FormUrlEncoded
    @PATCH("/albums/{id}")
    suspend fun updateAlbumTitle(
        @Path("id") id: Int,
        @Field("title") title: String
    ): Response<AlbumEntity>

    @PUT("/albums/{id}")
    suspend fun updateAlbum(
        @Path("id") id: Int,
        @Body albumEntity: AlbumEntity
    ): Response<AlbumEntity>
}