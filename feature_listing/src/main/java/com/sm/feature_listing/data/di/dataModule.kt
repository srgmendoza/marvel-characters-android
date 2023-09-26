package com.sm.feature_listing.data.di

import com.sm.feature_listing.data.ListingEndpoints
import com.sm.feature_listing.data.repo.ListingRepository
import com.sm.feature_listing.domain.repositories.CharacterRemoteRepository
import com.sm.network_core.NetworkCore
import org.koin.dsl.module

internal val dataModule = module {
    factory {
        getRepository(get())
    }
}

private fun getRepository(networkCore: NetworkCore): CharacterRemoteRepository =
    ListingRepository(getListingEndpoint(networkCore))

private fun getListingEndpoint(networkCore: NetworkCore): ListingEndpoints =
    networkCore.getCoreNetwork(
        "https://gateway.marvel.com/v1/public/",
        ListingEndpoints::class.java
    )
