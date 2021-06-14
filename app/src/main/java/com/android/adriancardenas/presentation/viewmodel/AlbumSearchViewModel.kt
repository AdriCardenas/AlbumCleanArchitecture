package com.android.adriancardenas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.adriancardenas.data.model.Album
import com.android.adriancardenas.data.model.AlbumTransition
import com.android.adriancardenas.domain.model.ErrorModel
import com.android.adriancardenas.domain.useCaseContract.FilterAlbumsUseCase
import com.android.adriancardenas.domain.useCaseContract.GetAlbumsUseCase
import kotlinx.coroutines.launch

class AlbumSearchViewModel(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val filterAlbumUseCase: FilterAlbumsUseCase
) : ViewModel() {

    private val scope = viewModelScope()

    private val _albumList = MutableLiveData<List<Album>>()
    val albumList: LiveData<List<Album>>
        get() = _albumList

    private val _error = MutableLiveData<ErrorModel>()
    val error: LiveData<ErrorModel>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _selected = MutableLiveData<AlbumTransition>()
    val selected: LiveData<AlbumTransition>
        get() = _selected

    private val _albumUpdated = MutableLiveData<Album>()
    val albumUpdated: LiveData<Album>
        get() = _albumUpdated

    private val _albumDelete = MutableLiveData<Album>()
    val albumDelete: LiveData<Album>
        get() = _albumDelete

    private val _querySearch = MutableLiveData<String>()
    val querySearch: LiveData<String>
        get() = _querySearch

    private val _filteredList = MutableLiveData<List<Album>>()
    val filteredList: LiveData<List<Album>>
        get() = _filteredList


    fun searchByName(query: String) {
        _querySearch.postValue(query)
    }

    fun filterByTitle(listToBeFiltered: List<Album>, query: String) {
        scope.launch {
            showLoading(true)

            filterAlbumUseCase.filterByName(query, listToBeFiltered).fold(
                ::handleError,
                ::handleListAlbumFiltered
            )
        }
    }

    fun searchAll(userId: String) {
        scope.launch {
            showLoading(true)

            getAlbumsUseCase.getAlbums(userId).fold(
                ::handleError,
                ::handleListAlbumResponse
            )
        }
    }

    private fun handleListAlbumResponse(list: List<Album>) {
        _albumList.postValue(list)
        showLoading(false)
    }

    private fun handleListAlbumFiltered(list: List<Album>) {
        _filteredList.postValue(list)
        showLoading(false)
    }

    private fun handleError(errorModel: ErrorModel) {
        _error.postValue(errorModel)
        showLoading(false)
    }

    private fun showLoading(isVisible: Boolean) {
        _loading.postValue(isVisible)
    }

    fun onItemSelected(albumTransition: AlbumTransition) {
        _selected.value = albumTransition
    }

    fun addToList(list: List<Album>, album: Album) {
        val newList = list.toMutableList()
        newList.add(album)
        _albumList.postValue(newList)
    }

    fun itemUpdated(album: Album) {
        _albumUpdated.postValue(album)
    }

    fun itemDeleted(album: Album) {
        _albumDelete.postValue(album)
    }

    fun updateAlbumList(list: List<Album>, album: Album) {
        val index = list.indexOfFirst { albumIterator -> albumIterator.id == album.id }

        if (index != -1) {
            val newList = list.toMutableList()
            newList[index] = album
            _albumList.postValue(newList)
        }
    }

    fun deleteAlbumList(list: List<Album>, album: Album) {
        val index = list.indexOfFirst { albumIterator -> albumIterator.id == album.id }

        if (index != -1) {
            val newList = list.toMutableList()
            newList.removeAt(index)
            _albumList.postValue(newList)
        }
    }

    override fun onCleared() {
        scope.job.cancel()
        super.onCleared()
    }

}