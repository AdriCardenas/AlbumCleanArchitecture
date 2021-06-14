package com.android.adriancardenas.app.view.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.transition.*
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.android.adriancardenas.R
import com.android.adriancardenas.app.utils.PARAM_USER
import com.android.adriancardenas.app.utils.showToast
import com.android.adriancardenas.app.view.fragment.AlbumDetailFragment
import com.android.adriancardenas.app.view.fragment.AlbumListFragment
import com.android.adriancardenas.data.model.AlbumTransition
import com.android.adriancardenas.databinding.AlbumSearchActivityBinding
import com.android.adriancardenas.presentation.viewmodel.AlbumSearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumSearchActivity : AppCompatActivity() {

    lateinit var binding: AlbumSearchActivityBinding

    private val albumSearchViewModel: AlbumSearchViewModel by viewModel()

    private var searchItem: MenuItem? = null

    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = intent.extras?.getString(PARAM_USER, "") ?: ""

        super.onCreate(savedInstanceState)
        binding = AlbumSearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = AlbumListFragment.newInstance(userId)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment)
        }

        albumSearchViewModel.searchAll(userId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_album_search_activity, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView?
        searchItem?.isVisible = false

        setupSearchView(searchView, searchManager)
        subcribeToViewModel()

        return true
    }

    private fun subcribeToViewModel() {
        albumSearchViewModel.albumList.observe(this, { list ->
            searchItem?.isVisible = list != null
        })

        albumSearchViewModel.selected.observe(this, { albumTransition ->
            if (albumTransition != null) openDetailForAlbum(albumTransition, searchItem)
        })
    }

    private fun setupSearchView(
        searchView: SearchView?,
        searchManager: SearchManager
    ) {
        searchView?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconified = false
            queryHint = getString(R.string.search_hint)

            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            albumSearchViewModel.searchByName(query)
                            searchItem?.collapseActionView()
                        } else {
                            showToast(context.getString(R.string.search_album_toolbar_error))
                        }

                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean = false
                }
            )
        }
    }

    class DetailsTransition : TransitionSet() {
        init {
            ordering = ORDERING_TOGETHER
            addTransition(ChangeBounds()).addTransition(ChangeTransform()).addTransition(
                ChangeImageTransform()
            )
        }
    }


    // We can improve navigation adding go back action in toolbar
    private fun openDetailForAlbum(transition: AlbumTransition, searchItem: MenuItem?) {
        val fragment = AlbumDetailFragment.newInstance(transition.album)

        fragment.sharedElementEnterTransition = DetailsTransition()
        fragment.enterTransition = Fade()
        fragment.sharedElementReturnTransition = DetailsTransition()

        searchItem?.isVisible = false

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            addSharedElement(transition.titleView, "title_${transition.album.id}")
            replace(R.id.fragment_container, fragment)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        searchItem?.isVisible = true
    }
}