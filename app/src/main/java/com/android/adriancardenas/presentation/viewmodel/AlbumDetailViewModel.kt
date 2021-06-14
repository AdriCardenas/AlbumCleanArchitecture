package com.android.adriancardenas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.data.model.Photo
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.useCaseContract.DeleteAlbumUseCase
import com.android.adriancardenas.domain.useCaseContract.GetPhotosByAlbumUseCase
import com.android.adriancardenas.domain.useCaseContract.UpdateAlbumTitleUseCase
import com.android.adriancardenas.domain.useCaseContract.UpdateAlbumUseCase
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val getPhotosByAlbumUseCase: GetPhotosByAlbumUseCase,
    private val updateAlbumTitleUseCase: UpdateAlbumTitleUseCase,
    private val updateAlbumUseCase: UpdateAlbumUseCase,
    private val deleteAlbumUseCase: DeleteAlbumUseCase
) : ViewModel() {

    private val scope = viewModelScope()

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private val _albumDelete = MutableLiveData<Album>()
    val albumDelete: LiveData<Album>
        get() = _albumDelete

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<ErrorModel>()
    val error: LiveData<ErrorModel>
        get() = _error

    fun loadPhotos(albumId: Int) {
        scope.launch {
            _loading.value = true

            getPhotosByAlbumUseCase.getPhotos(albumId).fold(
                ::handleError,
                ::handleResponse
            )
        }
    }

    fun updateAlbumTitle(albumId: Int, title:String){
        scope.launch {
            _loading.value = true

            updateAlbumTitleUseCase.updateAlbumTitle(albumId, title).fold(
                ::handleError,
                ::handleUpdatedAlbumResponse
            )
        }
    }

    fun deleteAlbum(albumId: Int, album: Album){
        scope.launch {
            _loading.value = true

            deleteAlbumUseCase.deleteAlbum(albumId).fold(
                ::handleError
            ) { handleDeleteAlbumResponse(album) }
        }
    }

    private fun handleDeleteAlbumResponse(album: Album) {
        _albumDelete.postValue(album)
        _loading.value = false
    }

    private fun handleUpdatedAlbumResponse(album:Album) {
        _album.postValue(album)
        _loading.value = false
    }

    private fun handleError(errorModel: ErrorModel) {
        _loading.value = false
        _error.postValue(errorModel)
    }

    private fun handleResponse(list: List<Photo>) {
        _photos.postValue(list)
        _loading.value = false
    }

    fun updateAlbum(albumId: Int, album: Album) {
        scope.launch {
            _loading.value = true

            updateAlbumUseCase.updateAlbum(albumId, album).fold(
                ::handleError,
                ::handleUpdatedAlbumResponse
            )
        }
    }

    override fun onCleared() {
        scope.job.cancel()
        super.onCleared()
    }
}