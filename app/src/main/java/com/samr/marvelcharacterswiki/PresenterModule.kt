package com.samr.marvelcharacterswiki

import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.usecases.ImagesUseCase
import com.samr.marvelcharacterswiki.ui.presenters.CharacterPresenter
import com.samr.marvelcharacterswiki.ui.presenters.CharacterPresenterImpl
import org.koin.dsl.module

val charactersPresenterModule = module {

    fun provideCharactersPresenterModule(
        charactersUseCase: CharactersUseCase,
        characterDetailUseCase: CharacterDetailUseCase,
        imagesUseCase: ImagesUseCase
    ): CharacterPresenter =
        CharacterPresenterImpl(
            charactersUseCase,
            characterDetailUseCase,
            imagesUseCase
        )

    single { provideCharactersPresenterModule(get(), get(), get()) }
}
