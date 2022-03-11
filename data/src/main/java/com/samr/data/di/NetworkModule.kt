package com.samr.data.di

import com.samr.data.BuildConfig
import com.samr.data.remote.endpoints.CharacterEndpoints
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


internal val networkModule = module {

    single() {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    single {
        get<Retrofit.Builder>()
            .build()
            .create(CharacterEndpoints::class.java)
    }
}