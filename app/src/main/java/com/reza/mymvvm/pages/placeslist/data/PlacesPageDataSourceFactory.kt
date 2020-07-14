package com.reza.mymvvm.pages.placeslist.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList




import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class PlacesPageDataSourceFactory @Inject constructor(
    private var latlong: String,
    private val dataSource: PlacesRemoteDataSource,
    private val dao: PlacesDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Place>() {

    private val liveData = MutableLiveData<PlacesPageDataSource>()

    override fun create(): DataSource<Int, Place> {
        val source =
            PlacesPageDataSource(latlong,dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 15
        private const val INITIAL_SIZE = 6

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(INITIAL_SIZE)
            .setPrefetchDistance(6)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}