package com.sm.feature_detail.presentation.di

import com.sm.feature_detail.DetailFeatureImpl
import com.sm.feature_detail_api.DetailsFeatureApi
import org.koin.dsl.module

internal val uIModule = module {
    single<DetailsFeatureApi> { DetailFeatureImpl() } //Exposing navigation to the DI system
}