package com.sm.feature_listing.domain.di

import com.sm.feature_listing.domain.usecases.GetPagedCharactersListUseCase
import org.koin.dsl.module

internal val domainModule = module { single { GetPagedCharactersListUseCase(get()) } }