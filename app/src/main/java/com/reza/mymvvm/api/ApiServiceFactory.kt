package com.reza.mymvvm.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceFactory {
    fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): ApiService = Retrofit.Builder()
        .baseUrl(ApiService.ENDPOINT)
        .client(okhttpClient)
        .addConverterFactory(converterFactory)
        .build()
        .create(ApiService::class.java)
}