package com.sm.feature_listing.di

import com.sm.feature_listing.data.di.dataModule
import com.sm.feature_listing.domain.di.domainModule
import com.sm.feature_listing.presentation.di.uiModule
import com.sm.network_core.di.networkCoreModule
import org.koin.dsl.module

val mainListingModule = module {}
    .plus(
        listOf(
            networkCoreModule,
            dataModule,
            domainModule,
            uiModule
        )
    )


