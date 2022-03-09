package com.samr.data.di

import org.koin.dsl.module

val MainDataModules = module() {}
    .plus(networkModule)
    .plus(databaseModule)
    .plus(repositoriesModules)

