package com.samr.marvelcharacterswiki

import com.samr.domain.repositories.CharactersRepository
import com.samr.data.repositories.DefaultCharacterRepo
import com.samr.data.repositories.DefaultImageRepository
import com.samr.data.repositories.DefaultCharacterDetailRepository
import com.samr.domain.repositories.CharacterDetailRepository
import com.samr.domain.repositories.ImagesRepository
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import org.koin.dsl.module

val charactersRepoModule = module {

    fun provideCharactersRepoModule(): CharactersRepository = DefaultCharacterRepo()

    single { provideCharactersRepoModule() }
}

val imagesRepoModule = module {

    fun provideImagesRepoModule(): ImagesRepository = DefaultImageRepository()

    single { provideImagesRepoModule() }
}

val characterDetailRepoModule = module {

    fun provideCharacterDetailRepoModule(): CharacterDetailRepository = DefaultCharacterDetailRepository()

    single { provideCharacterDetailRepoModule() }
}

val charactersUseCaseModule = module {

    fun provideCharactersUseCaseModule(repo: CharactersRepository) = CharactersUseCase(repo)
    single { provideCharactersUseCaseModule(get()) }
}

val characterDetailsUseCaseModule = module {

    fun provideCharacterDetailsUseCaseModule(repo: CharacterDetailRepository) = CharacterDetailUseCase(repo)
    single { provideCharacterDetailsUseCaseModule(get()) }
}

val imagesUseCaseModule = module {

    fun provideImagesUseCaseModule(repo: ImagesRepository) = ImagesUseCase(repo)
    single { provideImagesUseCaseModule(get()) }
}
