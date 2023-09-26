package com.sm.feature_listing.presentation.di

import com.sm.feature_listing.presentation.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val uiModule = module { viewModel { CharacterListViewModel(get()) } }