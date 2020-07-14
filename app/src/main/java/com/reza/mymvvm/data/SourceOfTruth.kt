package com.reza.mymvvm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.reza.mymvvm.data.Result.Status.ERROR
import com.reza.mymvvm.data.Result.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

/**
 * Our database is the single source of truth.
 * And UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun <T, A> resultLiveData(
    dbQuery: () -> LiveData<T>, networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading<T>())
        val source = dbQuery.invoke().map { Result.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == ERROR) {
            emit(Result.error<T>(responseStatus.message!!))
            emitSource(source)
        }


//        emit(Result.loading())
//        val queryResult = dbQuery.invoke()
//        queryResult.takeIf {
//            it.value != null
//        }?.map {
//                Result.success(it)
//            }?.apply {
//                emitSource(this)
//                return@liveData
//            }
//
//
//        val responseStatus = networkCall.invoke()
//        if (responseStatus.status == SUCCESS) {
//            saveCallResult(responseStatus.data!!)
//                .takeIf { queryResult.value != null }
//                .let { dbQuery.invoke() }
//                .map { Result.success(it) }
//                .apply { emitSource(this) }
//        } else if (responseStatus.status == ERROR) {
//            emit(Result.error(responseStatus.message!!))
//        }
    }