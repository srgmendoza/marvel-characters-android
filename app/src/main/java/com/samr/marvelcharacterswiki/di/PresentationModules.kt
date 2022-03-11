package com.samr.marvelcharacterswiki.di

import com.samr.marvelcharacterswiki.ui.characterDetail.CharacterDetailViewModel
import com.samr.marvelcharacterswiki.ui.charactersList.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { CharacterListViewModel(get()) }
    viewModel { CharacterDetailViewModel(get()) }
}