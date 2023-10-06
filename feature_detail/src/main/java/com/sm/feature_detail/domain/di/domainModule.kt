package com.sm.feature_detail.domain.di

import com.sm.feature_detail.domain.usecases.GetCharacterDetailByIdUseCase
import com.sm.feature_detail.domain.usecases.GetCharacterDetailByIdUseCaseImpl
import org.koin.dsl.module

internal val domainModule = module {
    factory<GetCharacterDetailByIdUseCase> { GetCharacterDetailByIdUseCaseImpl(get())}
}