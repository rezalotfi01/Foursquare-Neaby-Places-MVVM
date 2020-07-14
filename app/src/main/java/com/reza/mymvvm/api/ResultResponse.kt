package com.reza.mymvvm.api

import com.google.gson.annotations.SerializedName

data class ResultResponse<T>(
    @SerializedName("response")
    var response: T
)