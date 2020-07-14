package com.reza.mymvvm.di.component

import android.app.Application
import com.reza.mymvvm.App
import com.reza.mymvvm.di.modules.AppModule
import com.reza.mymvvm.di.modules.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(application: App)
}
