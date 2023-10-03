package com.sm.feature_search.data.di

import com.sm.feature_search.data.api.SearchEndpoints
import com.sm.feature_search.data.repo.SearchRepositoryImpl
import com.sm.feature_search.domain.repositories.SearchRepository
import com.sm.network_core.NetworkCore
import org.koin.dsl.module

internal val dataModule = module {
    factory<SearchRepository> {
        SearchRepositoryImpl(getSearchEndpoint(get()))
    }
}

private fun getSearchEndpoint(networkCore: NetworkCore): SearchEndpoints =
    networkCore.getCoreNetwork(
        baseUrl = "https://gateway.marvel.com/v1/public/",
        SearchEndpoints::class.java
    )