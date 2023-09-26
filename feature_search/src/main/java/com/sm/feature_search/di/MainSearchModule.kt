package com.sm.feature_search.di

import com.sm.feature_search.data.di.dataModule
import com.sm.feature_search.domain.di.domainModule
import com.sm.feature_search.presentation.di.presentationModule
import com.sm.network_core.di.networkCoreModule
import org.koin.dsl.module

val mainSearchModule = module {}
    .plus(
        listOf(
            networkCoreModule,
            dataModule,
            domainModule,
            presentationModule
        )
    )