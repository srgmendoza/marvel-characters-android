package com.sm.network_core

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal class NetworkCoreImpl : NetworkCore {

    init {
        Log.d("LOG", "HERE")
    }

    override fun <T> getCoreNetwork(baseUrl: String, endPoint: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(endPoint)

}