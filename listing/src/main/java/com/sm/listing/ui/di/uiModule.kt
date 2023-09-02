package com.sm.listing.ui.di

import com.sm.listing.ui.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val uiModule = module { viewModel { CharacterListViewModel(get()) } }