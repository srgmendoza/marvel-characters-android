package com.samr.data.services

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ImagesEndpoint {

    @GET
    fun getImage(@Url url: String): Deferred<ResponseBody>
}