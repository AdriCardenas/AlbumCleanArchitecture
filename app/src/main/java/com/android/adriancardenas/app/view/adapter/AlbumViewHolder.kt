package com.android.adriancardenas.app.view.adapter

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.adriancardenas.R
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.data.model.AlbumTransition
import com.android.adriancardenas.databinding.AlbumListItemHolderBinding

class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = AlbumListItemHolderBinding.bind(view)

    fun bind(item: Album, clickListener: (transition: AlbumTransition) -> Unit) {
        binding.title.text = item.title
        binding.id.text = itemView.context.getString(R.string.identifier_number, item.id)
        ViewCompat.setTransitionName( binding.title, "title_${item.id}")
        binding.card.setOnClickListener {
            clickListener(AlbumTransition(binding.title, item))
        }
    }
}