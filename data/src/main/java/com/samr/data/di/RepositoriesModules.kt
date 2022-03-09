package com.samr.data.di

import com.samr.data.local.repositories.CharacterLocalRepo
import com.samr.data.remote.repositories.CharacterRemoteRepo
import org.koin.dsl.module

val repositoriesModules = module {
    single { CharacterLocalRepo(get()) }
    single { CharacterRemoteRepo(get()) }
}
