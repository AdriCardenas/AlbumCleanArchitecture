package com.android.adriancardenas.app.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.adriancardenas.R
import com.android.adriancardenas.app.utils.PARAM_ALBUM
import com.android.adriancardenas.app.utils.showToast
import com.android.adriancardenas.app.view.adapter.PhotoAdapter
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.databinding.AlbumDetailFragmentBinding
import com.android.adriancardenas.presentation.viewmodel.AlbumDetailViewModel
import com.android.adriancardenas.presentation.viewmodel.AlbumSearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumDetailFragment : Fragment() {

    private var _binding: AlbumDetailFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val albumDetailViewModel by viewModel<AlbumDetailViewModel>()
    private val albumSearchViewModel by sharedViewModel<AlbumSearchViewModel>()

    private var album: Album? = null


    companion object {
        fun newInstance(album: Album): Fragment {
            val arguments = Bundle().apply {
                putParcelable(PARAM_ALBUM, album)
            }

            return AlbumDetailFragment().apply {
                this.arguments = arguments
            }
        }

        private const val SPAN_COUNT_PHOTOS = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumDetailFragmentBinding.inflate(inflater, container, false)

        album = arguments?.getParcelable(PARAM_ALBUM)
        if (album != null) {
            binding.titleText.transitionName = "title_${album!!.id}"
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleText.text = album?.title ?: "error"
        binding.titleInput.setText(album?.title ?: "error")

        if (album != null) {
            albumDetailViewModel.loadPhotos(album!!.id)

            val adapter = PhotoAdapter()

            subcribeToViewModel(adapter)

            binding.recyclerView.layoutManager =
                GridLayoutManager(context, SPAN_COUNT_PHOTOS, RecyclerView.VERTICAL, false)

            binding.recyclerView.adapter = adapter

            binding.sendPatchUpdate.setOnClickListener {
                if (!binding.titleInput.text.isNullOrEmpty()) {
                    binding.title.error = ""
                    albumDetailViewModel.updateAlbumTitle(
                        album!!.id,
                        binding.titleInput.text.toString()
                    )
                } else {
                    binding.title.error =
                        getString(R.string.field_invalid)
                }
            }

            binding.sendUpdate.setOnClickListener {
                if (!binding.titleInput.text.isNullOrEmpty()) {
                    binding.title.error = ""
                    albumDetailViewModel.updateAlbum(
                        album!!.id,
                        Album(album!!.id, album!!.userId, binding.titleInput.text.toString())
                    )
                } else {
                    binding.title.error =
                        getString(R.string.field_invalid)
                }
            }

            binding.sendDelete.setOnClickListener {
                albumDetailViewModel.deleteAlbum(album!!.id, album!!)
            }

            binding.edit.setOnClickListener {
                binding.titleText.visibility = View.GONE
                binding.edit.visibility = View.GONE
                binding.title.visibility = View.VISIBLE
                binding.sendUpdate.visibility = View.VISIBLE
                binding.sendDelete.visibility = View.VISIBLE
                binding.sendPatchUpdate.visibility = View.VISIBLE
            }
        }
    }

    private fun subcribeToViewModel(adapter: PhotoAdapter) {
        albumDetailViewModel.photos.observe(viewLifecycleOwner, { list ->
            if (list.isNotEmpty()) {
                adapter.submitList(list)
                binding.recyclerView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.GONE
            }
        })

        albumDetailViewModel.error.observe(viewLifecycleOwner, {
            if (it != null) {
                requireContext().showToast(getString(R.string.an_error_has_ocurred))
            }
        })

        albumDetailViewModel.loading.observe(viewLifecycleOwner, {
            binding.loadingView.visibility = if (it != null && it) View.VISIBLE else View.GONE
        })

        albumDetailViewModel.album.observe(viewLifecycleOwner, {
            if (it != null) {
                albumSearchViewModel.itemUpdated(it)
                requireActivity().onBackPressed()
            }
        })

        albumDetailViewModel.albumDelete.observe(viewLifecycleOwner, {
            if (it != null) {
                albumSearchViewModel.itemDeleted(it)
                requireActivity().onBackPressed()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}