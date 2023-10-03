package com.sm.feature_search.presentation.di

import com.sm.feature_search.presentation.ui.fragments.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val presentationModule = module {
    viewModel { SearchViewModel(get(), get()) }
}