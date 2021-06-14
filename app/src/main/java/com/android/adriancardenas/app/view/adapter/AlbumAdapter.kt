package com.android.adriancardenas.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.adriancardenas.R
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.data.model.AlbumTransition

class AlbumAdapter(private val clickListener: (transition: AlbumTransition) -> Unit) :
    ListAdapter<Album, AlbumViewHolder>(object :
        DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Album,
            newItem: Album
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        LayoutInflater.from(parent.context).run {
            val view = inflate(R.layout.album_list_item_holder, parent, false)
            AlbumViewHolder(view)
        }


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}