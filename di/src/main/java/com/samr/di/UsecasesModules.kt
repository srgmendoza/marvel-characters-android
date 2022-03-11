package com.samr.di

import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharacterListUsecase
import org.koin.dsl.module

val usecasesModule = module {
    single { CharacterListUsecase(get(), get()) }
    single { CharacterDetailUseCase(get()) }
}