package com.sm.listing.domain.di

import com.sm.listing.domain.usecases.CharacterListUsecase
import org.koin.dsl.module

internal val domainModule = module { single { CharacterListUsecase(get()) } }