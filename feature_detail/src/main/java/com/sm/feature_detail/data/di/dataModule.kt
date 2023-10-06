package com.sm.feature_detail.data.di

import com.sm.feature_detail.data.endpoints.DetailsEndpoint
import com.sm.feature_detail.data.repo.CharacterDetailsRepository
import com.sm.feature_detail.data.repo.CharacterDetailsRepositoryImpl
import com.sm.network_core.NetworkCore
import org.koin.dsl.module

internal val dataModule = module {
    factory {
        getRepository(get())
    }
}

private fun getRepository(networkCore: NetworkCore): CharacterDetailsRepository =
    CharacterDetailsRepositoryImpl(getDetailsEndpoint(networkCore))

private fun getDetailsEndpoint(networkCore: NetworkCore): DetailsEndpoint =
    networkCore.getCoreNetwork(
        "https://gateway.marvel.com/v1/public/",
        DetailsEndpoint::class.java
    )