package com.reza.mymvvm.pages.placeslist.data

import com.reza.mymvvm.BuildConfig
import com.reza.mymvvm.api.ApiService
import com.reza.mymvvm.api.BaseDataSource

import com.reza.mymvvm.util.API_DATE_FORMAT_PATTERN
import com.reza.mymvvm.util.common.DateUtil
import javax.inject.Inject


/**
 * Contains some Coroutine suspended functions and
 * Works with the Locations-Test API to get data.
 */

class PlacesRemoteDataSource @Inject constructor(private val service: ApiService) :
    BaseDataSource() {


    suspend fun fetchPlaces(lat_long: String, limit: Number, offset: Number) = getResult {
        service.getPlacesList(
            lat_long, limit, offset
            , BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET
            , DateUtil.getTodayDateFormatted(API_DATE_FORMAT_PATTERN)
        )
    }


}