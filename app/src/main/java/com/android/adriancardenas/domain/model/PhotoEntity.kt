package com.android.adriancardenas.domain.model

import com.android.adriancardenas.data.model.Photo

data class PhotoEntity(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
){
    fun transformToPhoto() = Photo(id, albumId, title, url, thumbnailUrl)
}