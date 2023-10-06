package com.sm.feature_detail.presentation.di

import com.sm.feature_detail.DetailFeatureImpl
import com.sm.feature_detail.presentation.composable_ui.screens.main.DetailsScreenViewModel
import com.sm.feature_detail_api.DetailsFeatureApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val uIModule = module {
    single<DetailsFeatureApi> { DetailFeatureImpl() } //Exposing navigation to the DI system
    viewModel { DetailsScreenViewModel(get()) }
}