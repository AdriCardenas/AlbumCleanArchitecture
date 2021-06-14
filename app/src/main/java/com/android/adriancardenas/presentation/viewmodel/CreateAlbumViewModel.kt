package com.android.adriancardenas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.useCaseContract.CreateAlbumUseCase
import kotlinx.coroutines.launch

class CreateAlbumViewModel(
    private val createAlbumUseCase: CreateAlbumUseCase
) : ViewModel() {

    private val scope = viewModelScope()

    private val _error = MutableLiveData<ErrorModel>()
    val error: LiveData<ErrorModel>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _albumCreated = MutableLiveData<Album>()
    val albumCreated: LiveData<Album>
        get() = _albumCreated

    fun createAlbum(album:Album){
        scope.launch {
            _loading.postValue(true)

            createAlbumUseCase.createAlbum(album).fold(
                ::handleError,
                ::handleResponse
            )
        }
    }

    private fun handleResponse(album: Album) {
        _albumCreated.postValue(album)
    }

    private fun handleError(errorModel: ErrorModel) {
        _error.postValue(errorModel)
    }

    override fun onCleared() {
        scope.job.cancel()
        super.onCleared()
    }

}