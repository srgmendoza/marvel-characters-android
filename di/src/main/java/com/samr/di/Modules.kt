package com.samr.di

import com.samr.data.di.dataModule
import org.koin.dsl.module

val dataDomainModule = module {  }
    .plus(dataModule)
    .plus(repositoriesModules)
    .plus(usecasesModule)
