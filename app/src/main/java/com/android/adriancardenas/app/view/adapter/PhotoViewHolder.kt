package com.android.adriancardenas.app.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.adriancardenas.R
import com.android.adriancardenas.data.model.Photo
import com.android.adriancardenas.databinding.PhotoListItemHolderBinding

class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PhotoListItemHolderBinding.bind(view)

    fun bind(photo: Photo) {
        binding.image.load(photo.url) {
            crossfade(true)
            error(R.drawable.gray_background_error)
            placeholder(R.drawable.gray_background_error)
        }

        if (photo.title.isNotEmpty()) {
            binding.title.visibility = View.VISIBLE
            binding.title.text = photo.title
        } else {
            binding.title.visibility = View.GONE
        }
    }
}