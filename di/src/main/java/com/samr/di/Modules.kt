package com.samr.di

import com.samr.data.repositories.CharacterDetailRepoImpl
import com.samr.data.repositories.CharactersListRepoImpl
import com.samr.data.repositories.ImageRepoImpl
import com.samr.domain.repositories.CharacterDetailRepo
import com.samr.domain.repositories.CharactersListRepo
import com.samr.domain.repositories.ImageRepo
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import org.koin.dsl.module

val charactersRepoModule = module {

    fun provideCharactersRepoModule(): CharactersListRepo = CharactersListRepoImpl()
    single { provideCharactersRepoModule() }
}

val imagesRepoModule = module {

    fun provideImagesRepoModule(): ImageRepo = ImageRepoImpl()
    single { provideImagesRepoModule() }
}

val characterDetailRepoModule = module {

    fun provideCharacterDetailRepoModule(): CharacterDetailRepo = CharacterDetailRepoImpl()
    single { provideCharacterDetailRepoModule() }
}

val charactersUseCaseModule = module {

    fun provideCharactersUseCaseModule(repo: CharactersListRepo) = CharactersUseCase(repo)
    single { provideCharactersUseCaseModule(get()) }
}

val characterDetailsUseCaseModule = module {

    fun provideCharacterDetailsUseCaseModule(repo: CharacterDetailRepo) = CharacterDetailUseCase(repo)
    single { provideCharacterDetailsUseCaseModule(get()) }
}

val imagesUseCaseModule = module {

    fun provideImagesUseCaseModule(repo: ImageRepo) = ImagesUseCase(repo)
    single { provideImagesUseCaseModule(get()) }
}