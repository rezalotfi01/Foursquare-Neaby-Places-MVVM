package com.reza.mymvvm.pages.placeslist.data

import androidx.paging.PageKeyedDataSource
import com.reza.mymvvm.data.Result
import com.reza.mymvvm.util.extensions.logDebug
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 * Source for places list pagination via paging library
 */
class PlacesPageDataSource @Inject constructor(
    private var latlong: String,
    private val dataSource: PlacesRemoteDataSource,
    private val dao: PlacesDao,
    private val scope: CoroutineScope) : PageKeyedDataSource<Int, Place>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Place>
    ) {
        fetchData( latlong,1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Place>) {
        val page = params.key
        fetchData(latlong,page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Place>) {
        val page = params.key
        fetchData(latlong,page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(latlong: String, offset: Int, limit: Int, callback: (List<Place>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            logDebug("limit is : $limit and offset is : ${offset * limit}")
            val response = dataSource.fetchPlaces(latlong, limit, offset * limit)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!.response.groups.first().items.map { it.venue }
                dao.insertAll(results)
                callback(results)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        //networkState.postValue(NetworkState.FAILED)
    }

}