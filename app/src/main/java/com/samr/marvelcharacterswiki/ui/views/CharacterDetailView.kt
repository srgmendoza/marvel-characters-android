package com.samr.marvelcharacterswiki.ui.views

import com.samr.marvelcharacterswiki.models.CharacterDetailModel


interface CharacterDetailView {

    fun onCharacterDetailReceived(character: CharacterDetailModel)
    fun onError(errorMessage: String)
}