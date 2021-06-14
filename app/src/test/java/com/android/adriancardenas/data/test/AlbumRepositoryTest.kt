package com.android.adriancardenas.data.test

import com.android.adriancardenas.data.datasource.FakeApiDataSource
import com.android.adriancardenas.data.repository.AlbumRepositoryImpl
import com.android.adriancardenas.data.utils.MainCoroutineRule
import com.android.adriancardenas.domain.repositoryContract.AlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumRepositoryTest {

    private lateinit var albumRepository: AlbumRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        albumRepository = AlbumRepositoryImpl(FakeApiDataSource())
    }

    @Test
    fun returnErrorWhenDeleteWithNonPossitiveId() = mainCoroutineRule.runBlockingTest {
        val response = albumRepository.deleteAlbum(-1)

        assert(response.isLeft)
    }

    @Test
    fun returnValidResponseWithValidId() = mainCoroutineRule.runBlockingTest {
        val response = albumRepository.deleteAlbum(100)

        assert(response.isRight)
    }
}