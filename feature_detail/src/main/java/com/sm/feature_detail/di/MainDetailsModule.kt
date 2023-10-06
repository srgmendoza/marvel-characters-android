package com.sm.feature_detail.di

import com.sm.feature_detail.data.di.dataModule
import com.sm.feature_detail.domain.di.domainModule
import com.sm.feature_detail.presentation.di.uIModule
import com.sm.network_core.di.networkCoreModule
import org.koin.dsl.module

val mainDetailsModule = module {}
    .plus(
        listOf(
            networkCoreModule,
            dataModule,
            domainModule,
            uIModule
        )
    )