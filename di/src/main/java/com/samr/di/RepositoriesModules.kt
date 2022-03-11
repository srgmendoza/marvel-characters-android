package com.samr.di

import com.samr.data.local.Database
import com.samr.data.local.repositories.CharacterLocalRepoImpl
import com.samr.domain.repositories.CharacterLocalRepository
import com.samr.data.remote.endpoints.CharacterEndpoints
import com.samr.data.remote.repositories.CharacterRemoteRepoImpl
import com.samr.domain.repositories.CharacterRemoteRepository
import org.koin.dsl.module

val repositoriesModules = module {
    single { provideCharacterLocalRepo(get()) }
    single { provideCharacterRemoteRepo(get()) }
}

fun provideCharacterLocalRepo(database: Database): CharacterLocalRepository {
    return CharacterLocalRepoImpl(database)
}

fun provideCharacterRemoteRepo(endpoints: CharacterEndpoints): CharacterRemoteRepository {
    return CharacterRemoteRepoImpl(endpoints)
}