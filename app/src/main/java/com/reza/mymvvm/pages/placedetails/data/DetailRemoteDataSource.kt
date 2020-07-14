package com.reza.mymvvm.pages.placedetails.data

import com.reza.mymvvm.BuildConfig
import com.reza.mymvvm.api.ApiService
import com.reza.mymvvm.api.BaseDataSource
import com.reza.mymvvm.util.API_DATE_FORMAT_PATTERN
import com.reza.mymvvm.util.common.DateUtil
import javax.inject.Inject

/**
 * Contains some Coroutine suspended functions and
 * Works with the Locations-MVVM API to get data.
 */

class DetailRemoteDataSource @Inject constructor(private val service: ApiService) :
    BaseDataSource() {

    suspend fun fetchDetail(id: String) =
        getResult { service.getDetails(id, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET,
            DateUtil.getTodayDateFormatted(API_DATE_FORMAT_PATTERN)) }
}