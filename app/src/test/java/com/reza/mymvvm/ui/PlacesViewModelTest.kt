package com.reza.mymvvm.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.reza.mymvvm.pages.placeslist.data.PlacesRepository
import com.reza.mymvvm.pages.placeslist.ui.PlacesViewModel


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class PlacesViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val latLong = "35.773693, 51.379578"
    private val repository = mock(PlacesRepository::class.java)
    private var viewModel = PlacesViewModel(repository, coroutineScope)

    @Test
    fun testNull() {
        assertThat(viewModel.connectivityAvailable, notNullValue())
        verify(repository, never()).observePagedList(false,latLong, coroutineScope)
        verify(repository, never()).observeLastLatLong()
    }

    @Test
    fun doNotFetchWithoutObservers() {
        verify(repository, never()).observePagedList(false, latLong, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.connectivityAvailable = true

        verify(repository, never()).observePagedList(true, latLong, coroutineScope)
    }

}