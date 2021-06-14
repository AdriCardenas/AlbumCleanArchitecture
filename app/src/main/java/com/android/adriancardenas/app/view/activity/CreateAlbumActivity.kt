package com.android.adriancardenas.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.adriancardenas.R
import com.android.adriancardenas.app.utils.PARAM_ALBUM
import com.android.adriancardenas.app.utils.PARAM_ID
import com.android.adriancardenas.app.utils.showToast
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.databinding.CreateAlbumActivityBinding
import com.android.adriancardenas.presentation.viewmodel.CreateAlbumViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


// This activity only have the mission of create a new Album, we needn't craete a single fragment
// to do this
class CreateAlbumActivity : AppCompatActivity() {

    private lateinit var binding: CreateAlbumActivityBinding

    private lateinit var userId: String

    private val createAlbumViewModel by viewModel<CreateAlbumViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = intent.extras?.getString(PARAM_ID) ?: ""

        super.onCreate(savedInstanceState)
        binding = CreateAlbumActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupCreateAlbum()
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        createAlbumViewModel.error.observe(this, {
            if (it != null) showToast(getString(R.string.an_error_has_ocurred))
        })

        createAlbumViewModel.loading.observe(this, {
            binding.loadingView.visibility = if (it != null && it) View.VISIBLE else View.GONE
        })

        createAlbumViewModel.albumCreated.observe(this, {
            if (it != null) {
                val resultIntent = Intent()
                resultIntent.putExtra(PARAM_ALBUM, it)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })
    }

    private fun setupCreateAlbum() {
        binding.createButton.setOnClickListener {
            val userId = userId.toIntOrNull()

            if (userId == null) {
                showToast(getString(R.string.invalid_user_id_creating_album))
            } else {
                when {
                    binding.idInput.text == null -> binding.id.error =
                        getString(R.string.field_required)
                    binding.titleInput.text == null -> binding.title.error =
                        getString(R.string.field_required)

                    else -> {
                        val id = binding.idInput.text.toString().toIntOrNull()
                        val title = binding.titleInput.text.toString()

                        when {
                            id == null -> binding.id.error = getString(R.string.field_invalid)
                            title.isBlank() -> binding.title.error =
                                getString(R.string.field_invalid)
                            else -> {
                                binding.id.error = ""
                                binding.title.error = ""
                                createAlbumViewModel.createAlbum(Album(id, userId, title))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}