package com.sm.feature_search.presentation.di

import com.sm.feature_search.SearchFeatureImpl
import com.sm.feature_search_api.SearchFeatureApi
import org.koin.dsl.module

internal val presentationModule = module {
    single<SearchFeatureApi> { SearchFeatureImpl() } //Exposing navigation to the DI system
}