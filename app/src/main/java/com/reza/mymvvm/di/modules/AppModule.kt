package com.reza.mymvvm.di.modules

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.reza.mymvvm.api.ApiService
import com.reza.mymvvm.api.ApiServiceFactory
import com.reza.mymvvm.data.AppDatabase
import com.reza.mymvvm.di.CoroutineIO

import com.reza.mymvvm.pages.placedetails.data.DetailRemoteDataSource
import com.reza.mymvvm.pages.placeslist.data.PlacesRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = ApiServiceFactory.createRetrofit(okhttpClient, converterFactory)

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @CoroutineIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Singleton
    @Provides
    fun provideContext(app: Application) = app.applicationContext

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)!!


    @Singleton
    @Provides
    fun providePlacesRemoteDataSource(apiService: ApiService) = PlacesRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun providePlacesDao(db: AppDatabase) = db.placesDao()

    @Singleton
    @Provides
    fun provideDetailRemoteDataSource(apiService: ApiService) = DetailRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideDetailDao(db: AppDatabase) = db.detailDao()

}
