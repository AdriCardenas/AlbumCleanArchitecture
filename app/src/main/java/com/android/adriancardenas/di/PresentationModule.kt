package com.android.adriancardenas.di

import com.android.adriancardenas.domain.useCaseContract.*
import com.android.adriancardenas.presentation.usecases.*
import com.android.adriancardenas.presentation.viewmodel.AlbumDetailViewModel
import com.android.adriancardenas.presentation.viewmodel.AlbumSearchViewModel
import com.android.adriancardenas.presentation.viewmodel.CreateAlbumViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AlbumSearchViewModel(get(), get())
    }

    viewModel {
        AlbumDetailViewModel(get(), get(), get(), get())
    }

    viewModel {
        CreateAlbumViewModel(get())
    }
}

val useCasesModule = module {
    factory<GetAlbumsUseCase> { GetAlbumsUserCaseImpl(get(), Dispatchers.IO) }
    factory<FilterAlbumsUseCase> { FilterAlbumUseCaseImpl(Dispatchers.Default) }
    factory<GetPhotosByAlbumUseCase> { GetPhotosByAlbumUseCaseImpl(get(), Dispatchers.IO) }
    factory<CreateAlbumUseCase> { CreateAlbumUseCaseImpl(get(), Dispatchers.IO) }
    factory<UpdateAlbumTitleUseCase> { UpdateAlbumTitleUseCaseImpl(get(), Dispatchers.IO) }
    factory<UpdateAlbumUseCase> { UpdateAlbumUseCaseImpl(get(), Dispatchers.IO) }
    factory<DeleteAlbumUseCase> { DeleteAlbumUseCaseImpl(get(), Dispatchers.IO) }
}