package com.reza.mymvvm.api


import com.reza.mymvvm.pages.placedetails.data.PlaceDetailModel
import com.reza.mymvvm.pages.placeslist.data.PlacesExploreModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface ApiService {

    companion object {
        const val ENDPOINT = "https://api.foursquare.com/v2/"
    }

    @GET("venues/explore")
    suspend fun getPlacesList(
        @Query("ll") lat_long: String,
        @Query("limit") limit: Number,
        @Query("offset") offset: Number,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") date: String
    ): Response<ResultResponse<PlacesExploreModel>>

    @GET("venues/{VENUE_ID}")
    suspend fun getDetails(
        @Path("VENUE_ID") id: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") date: String
    ): Response<ResultResponse<PlaceDetailModel>>
}
