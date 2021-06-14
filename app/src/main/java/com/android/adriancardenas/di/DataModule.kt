package com.android.adriancardenas.di

import com.android.adriancardenas.data.repository.AlbumRepositoryImpl
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import org.koin.dsl.module

val dataModule = module {
    single<AlbumRepository> { AlbumRepositoryImpl(get()) }
}