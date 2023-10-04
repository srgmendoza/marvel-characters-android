package com.sm.feature_listing.presentation.di

import com.sm.feature_listing.ListingFeatureImpl
import com.sm.feature_listing.presentation.CharacterListViewModel
import com.sm.feature_listing_api.ListingFeatureApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val uiModule = module {
    single<ListingFeatureApi> { ListingFeatureImpl() } //Exposing navigation to the DI system
    viewModel { CharacterListViewModel(get()) }
}