package com.android.adriancardenas.app.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.adriancardenas.R
import com.android.adriancardenas.app.utils.PARAM_ALBUM
import com.android.adriancardenas.app.utils.PARAM_ID
import com.android.adriancardenas.app.utils.showToast
import com.android.adriancardenas.app.view.activity.CreateAlbumActivity
import com.android.adriancardenas.app.view.adapter.AlbumAdapter
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.databinding.AlbumListFragmentBinding
import com.android.adriancardenas.presentation.viewmodel.AlbumSearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AlbumListFragment : Fragment() {

    private var _binding: AlbumListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val albumSearchViewModel by sharedViewModel<AlbumSearchViewModel>()

    private lateinit var userId: String
    private lateinit var adapter: AlbumAdapter

    private var list = listOf<Album>()

    companion object {
        fun newInstance(userId: String): AlbumListFragment {
            val args = Bundle()
            args.putString(PARAM_ID, userId)

            val fragment = AlbumListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userId = requireArguments().getString(PARAM_ID) ?: ""
        _binding = AlbumListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AlbumAdapter(clickListener = { transition ->
            albumSearchViewModel.onItemSelected(transition)
        })

        subcribeToViewModel(adapter)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.recyclerView.adapter = adapter

        if (userId.isNotEmpty()) {
            binding.fab.setOnClickListener {
                val intent = Intent(requireContext(), CreateAlbumActivity::class.java)
                intent.putExtra(PARAM_ID, userId)
                resultLauncher.launch(intent)
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val album = data?.getParcelableExtra<Album>(PARAM_ALBUM)
                if (album != null)
                    addAlbumToList(album)
            }
        }

    private fun addAlbumToList(album: Album) {
        albumSearchViewModel.addToList(list, album)
    }

    // Associating lifecycle to viewmodel variables
    private fun subcribeToViewModel(adapter: AlbumAdapter) {
        albumSearchViewModel.albumList.observe(viewLifecycleOwner, { list ->
            this.list = list
            showOrHideAlbumList(this.list, adapter)
        })

        albumSearchViewModel.filteredList.observe(viewLifecycleOwner, { list ->
            showOrHideAlbumList(list, adapter)
        })

        albumSearchViewModel.error.observe(viewLifecycleOwner, { error ->
            if (error != null) requireContext().showToast(getString(R.string.an_error_has_ocurred))

        })

        albumSearchViewModel.loading.observe(viewLifecycleOwner, { loading ->
            binding.loadingView.visibility =
                if (loading != null && loading) View.VISIBLE else View.GONE
        })

        albumSearchViewModel.albumUpdated.observe(viewLifecycleOwner, { album ->
            if (album != null)
                albumSearchViewModel.updateAlbumList(list, album)
        })

        albumSearchViewModel.querySearch.observe(viewLifecycleOwner, { query ->
            if (!query.isNullOrBlank())
                albumSearchViewModel.filterByTitle(list, query)
        })

        albumSearchViewModel.albumDelete.observe(viewLifecycleOwner, { album ->
            if (album != null)
                albumSearchViewModel.deleteAlbumList(list, album)
        })
    }

    private fun showOrHideAlbumList(list: List<Album>, adapter: AlbumAdapter) {
        if (list.isNotEmpty()) {
            adapter.submitList(list)
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}