package com.sm.feature_search.domain.di

import com.sm.feature_search.domain.usecases.GetFourRandomCharactersUseCase
import com.sm.feature_search.domain.usecases.GetFourRandomCharactersUseCaseImpl
import com.sm.feature_search.domain.usecases.SearchByNameUseCase
import com.sm.feature_search.domain.usecases.SearchByNameUseCaseImpl
import org.koin.dsl.module

internal val domainModule = module {
    single<SearchByNameUseCase> { SearchByNameUseCaseImpl(get()) }
    single<GetFourRandomCharactersUseCase> { GetFourRandomCharactersUseCaseImpl() }
}