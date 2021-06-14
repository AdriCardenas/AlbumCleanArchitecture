package com.android.adriancardenas.domain.model

import com.android.adriancardenas.data.model.Album

data class AlbumEntity(
    val id: Int,
    val userId: Int,
    val title: String
){
    fun transformToAlbum() = Album(id, userId, title)
}