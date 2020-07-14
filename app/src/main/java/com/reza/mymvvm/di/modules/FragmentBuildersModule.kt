package com.reza.mymvvm.di.modules




import com.reza.mymvvm.pages.placedetails.ui.DetailFragment
import com.reza.mymvvm.pages.placeslist.ui.PlacesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributePlacesFragment(): PlacesFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailFragment
}
