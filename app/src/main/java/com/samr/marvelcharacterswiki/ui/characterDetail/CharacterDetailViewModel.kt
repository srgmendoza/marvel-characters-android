package com.samr.marvelcharacterswiki.ui.characterDetail

import androidx.lifecycle.ViewModel
import com.samr.domain.usecases.CharacterDetailUseCase

class CharacterDetailViewModel(private val detailsUseCase: CharacterDetailUseCase): ViewModel() {

    fun getCharacterById(id: String) = detailsUseCase.getCharacterById(id)
}