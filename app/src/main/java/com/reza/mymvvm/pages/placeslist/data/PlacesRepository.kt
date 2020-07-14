package com.reza.mymvvm.pages.placeslist.data

import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import com.reza.mymvvm.util.SP_KEY_LAST_LOCATION
import com.reza.mymvvm.util.extensions.logDebug
import com.reza.mymvvm.util.extensions.stringLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class PlacesRepository @Inject constructor(private val dao: PlacesDao,
                                           private val placesRemoteDataSource: PlacesRemoteDataSource,
                                           private val sharedPreferences: SharedPreferences) {

    fun observePagedList(connectivityAvailable: Boolean, latLong: String
                         , coroutineScope: CoroutineScope) =
        if (connectivityAvailable || isDistanceOK(latLong))
            observeRemotePagedList(latLong,coroutineScope)
        else
            observeLocalPagedList()

    fun isDistanceOK(latLong: String): Boolean{
        val distance = FloatArray(2)

        val loc1 = sharedPreferences.getString(SP_KEY_LAST_LOCATION,"0,0")!!.split(',')
        val loc2 = latLong.split(',')
        Location.distanceBetween(
            loc1[0].trim().toDouble(), loc1[1].trim().toDouble(),
            loc2[0].trim().toDouble(), loc2[1].trim().toDouble(),
            distance
        )

        return distance[0] > 100
    }

    private fun observeLocalPagedList(): LiveData<PagedList<Place>> {
        val dataSourceFactory = dao.getPagedPlaces()
        dataSourceFactory.map {
            logDebug("database item name is ${it.name} and id : $it.id")
        }
        return LivePagedListBuilder(dataSourceFactory,
            PlacesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedList(latLong: String,ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Place>> {
        val dataSourceFactory =
            PlacesPageDataSourceFactory(latLong,placesRemoteDataSource, dao, ioCoroutineScope)

        return LivePagedListBuilder(dataSourceFactory,
            PlacesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeLastLatLong(): LiveData<String>
            = sharedPreferences.stringLiveData(SP_KEY_LAST_LOCATION,"")

    fun saveLastLocation(latLong: String)
            = sharedPreferences.edit().putString(SP_KEY_LAST_LOCATION,latLong).apply()
}
