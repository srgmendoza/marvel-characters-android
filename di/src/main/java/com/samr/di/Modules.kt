package com.samr.di

import com.samr.data.remote.repositories.CharacterDetailRepoImpl
import com.samr.data.remote.repositories.CharactersListRepoImpl
import com.samr.data.remote.repositories.ImageRepoImpl
import com.samr.data.remote.repositories.CharacterRemoteRepo
import com.samr.data.remote.repositories.ImageService
import com.samr.domain.repositories.CharacterDetailRepo
import com.samr.domain.repositories.CharactersListRepo
import com.samr.domain.repositories.ImageRepo
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import org.koin.dsl.module

val charactersRepoModule = module {

    fun provideCharactersRepoModule(service: CharacterRemoteRepo): CharactersListRepo = CharactersListRepoImpl(service)
    single { provideCharactersRepoModule(get()) }
}

val imagesRepoModule = module {

    fun provideImagesRepoModule(service: ImageService): ImageRepo = ImageRepoImpl(service)
    single { provideImagesRepoModule(get()) }
}

val characterDetailRepoModule = module {

    fun provideCharacterDetailRepoModule(service: CharacterRemoteRepo): CharacterDetailRepo = CharacterDetailRepoImpl(service)
    single { provideCharacterDetailRepoModule(get()) }
}

val charactersUseCaseModule = module {

    fun provideCharactersUseCaseModule(repo: CharactersListRepo) = CharactersUseCase(repo)
    factory { provideCharactersUseCaseModule(get()) }
}

val characterDetailsUseCaseModule = module {

    fun provideCharacterDetailsUseCaseModule(repo: CharacterDetailRepo) = CharacterDetailUseCase(repo)
    single { provideCharacterDetailsUseCaseModule(get()) }
}

val imagesUseCaseModule = module {

    fun provideImagesUseCaseModule(repo: ImageRepo) = ImagesUseCase(repo)
    single { provideImagesUseCaseModule(get()) }
}

val characterServiceModule = module {

    fun provideCharacterServiceModule() = CharacterRemoteRepo()
    single { provideCharacterServiceModule() }
}

val imageServiceModule = module {

    fun provideImageServiceModule() = ImageService()
    single { provideImageServiceModule() }
}
