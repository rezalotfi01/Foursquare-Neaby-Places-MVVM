package com.reza.mymvvm.repository


import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.reza.mymvvm.api.ApiService
import com.reza.mymvvm.data.AppDatabase
import com.reza.mymvvm.pages.placeslist.data.PlacesDao
import com.reza.mymvvm.pages.placeslist.data.PlacesRemoteDataSource
import com.reza.mymvvm.pages.placeslist.data.PlacesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class PlacesRepositoryTest {
    private lateinit var repository: PlacesRepository
    private val dao = mock(PlacesDao::class.java)
    private val service = mock(ApiService::class.java)
    private val remoteDataSource = PlacesRemoteDataSource(service)
    private val sharedPreferences = mock(SharedPreferences::class.java)
    private val mockRemoteDataSource = spy(remoteDataSource)


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.placesDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = PlacesRepository(dao, remoteDataSource,sharedPreferences)
    }

    @Test
    fun loadPlacesFromNetwork() {
        runBlocking {
            repository.observePagedList(connectivityAvailable = true,
                    latLong = "-122.0839895, 37.4219779", coroutineScope = coroutineScope)

            verify(dao, never()).getPagedPlaces()
            verifyNoInteractions(dao)
        }
    }

}