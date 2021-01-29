package com.samr.di

import com.samr.data.repositories.CharacterDetailRepoImpl
import com.samr.data.repositories.CharactersListRepoImpl
import com.samr.data.repositories.ImageRepoImpl
import com.samr.data.services.CharacterService
import com.samr.data.services.ImageService
import com.samr.domain.repositories.CharacterDetailRepo
import com.samr.domain.repositories.CharactersListRepo
import com.samr.domain.repositories.ImageRepo
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import org.koin.dsl.module

val charactersRepoModule = module {

    fun provideCharactersRepoModule(service: CharacterService): CharactersListRepo = CharactersListRepoImpl(service)
    single { provideCharactersRepoModule(get()) }
}

val imagesRepoModule = module {

    fun provideImagesRepoModule(service: ImageService): ImageRepo = ImageRepoImpl(service)
    single { provideImagesRepoModule(get()) }
}

val characterDetailRepoModule = module {

    fun provideCharacterDetailRepoModule(service: CharacterService): CharacterDetailRepo = CharacterDetailRepoImpl(service)
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

    fun provideCharacterServiceModule() = CharacterService()
    single{ provideCharacterServiceModule()}
}

val imageServiceModule = module {

    fun provideImageServiceModule() = ImageService()
    single{ provideImageServiceModule()}
}