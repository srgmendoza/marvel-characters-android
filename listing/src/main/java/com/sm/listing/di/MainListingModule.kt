package com.sm.listing.di

import com.sm.listing.data.di.dataModule
import com.sm.listing.domain.di.domainModule
import com.sm.listing.ui.di.uiModule
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


