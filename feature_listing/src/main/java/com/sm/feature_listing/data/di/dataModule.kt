package com.sm.feature_listing.data.di

import com.sm.feature_listing.data.endpoints.ListingEndpoints
import com.sm.feature_listing.data.repo.CharactersListRemoteRepositoryImpl
import com.sm.feature_listing.data.repo.CharactersListRemoteRepository
import com.sm.network_core.NetworkCore
import org.koin.dsl.module

internal val dataModule = module {
    factory {
        getRepository(get())
    }
}

private fun getRepository(networkCore: NetworkCore): CharactersListRemoteRepository =
    CharactersListRemoteRepositoryImpl(getListingEndpoint(networkCore))

private fun getListingEndpoint(networkCore: NetworkCore): ListingEndpoints =
    networkCore.getCoreNetwork(
        "https://gateway.marvel.com/v1/public/",
        ListingEndpoints::class.java
    )
