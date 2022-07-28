package com.mihir.testing.remote

import com.mihir.testing.other.Constants
import com.mihir.testing.remote.responses.PackageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PixalbayAPI {

    @GET("v1/search")
    fun searchForImages(
        @Query("query") value : String,
        @Header("Authorization") apikey : String = Constants.API_KEY
    ) : Response<PackageResponse>

//    @GET("v1/photos/")
//    fun getImage(@)
}