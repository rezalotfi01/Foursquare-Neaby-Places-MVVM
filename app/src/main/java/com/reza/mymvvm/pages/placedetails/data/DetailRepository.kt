package com.reza.mymvvm.pages.placedetails.data

import androidx.lifecycle.distinctUntilChanged
import com.reza.mymvvm.data.resultLiveData

import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class DetailRepository @Inject constructor(
    private val dao: DetailDao, private val detailRemoteDataSource: DetailRemoteDataSource) {

    fun observeDetail(id: String) = resultLiveData(
        dbQuery = {
            dao.getDetail(id)
        },
        networkCall = {
            detailRemoteDataSource.fetchDetail(id)
        },
        saveCallResult = {
            dao.insertDetail(it.response.venue)
        })
        .distinctUntilChanged()

}