package com.reza.mymvvm.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reza.mymvvm.di.ViewModelFactory
import com.reza.mymvvm.di.ViewModelKey
import com.reza.mymvvm.pages.placedetails.ui.DetailViewModel


import com.reza.mymvvm.pages.placeslist.ui.PlacesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    abstract fun bindPlacesViewModel(viewModel: PlacesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
