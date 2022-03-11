package com.samr.data.di

import org.koin.dsl.module

val dataModule = module() {}
    .plus(networkModule)
    .plus(databaseModule)

