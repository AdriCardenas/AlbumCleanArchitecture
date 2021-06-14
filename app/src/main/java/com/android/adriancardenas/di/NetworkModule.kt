package com.android.adriancardenas.di

import com.android.adriancardenas.app.network.createNetworkClient
import com.android.adriancardenas.data.api.AlbumApi
import com.android.adriancardenas.data.api.AlbumApiService
import com.android.adriancardenas.data.datasource.AlbumDatasource
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModules = module {
    single<Retrofit> { createNetworkClient(BASE_URL) }
    single<AlbumApiService> { get<Retrofit>().create(AlbumApiService::class.java) }
    single<AlbumDatasource> { AlbumApi(get()) }
}

private const val BASE_URL = "https://jsonplaceholder.typicode.com"