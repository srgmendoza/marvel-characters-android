package com.samr.marvelcharacterswiki.di

import com.samr.di.dataDomainModule
import org.koin.dsl.module

val mainModule = module {}
    .plus(presentationModule)
    .plus(dataDomainModule)