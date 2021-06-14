package com.android.adriancardenas.data.model

import android.os.Parcelable
import android.widget.TextView
import com.android.adriancardenas.domain.model.AlbumEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val id: Int,
    val userId: Int,
    val title: String
) : Parcelable{
    fun albumToAlbumEntity() = AlbumEntity(id, userId, title)
}

data class AlbumTransition(
    val titleView: TextView,
    val album: Album
)