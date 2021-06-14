package com.android.adriancardenas.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.adriancardenas.R
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.data.model.Photo

class PhotoAdapter() :
    ListAdapter<Photo, PhotoViewHolder>(object :
        DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Photo,
            newItem: Photo
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        LayoutInflater.from(parent.context).run {
            val view = inflate(R.layout.photo_list_item_holder, parent, false)
            PhotoViewHolder(view)
        }


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}