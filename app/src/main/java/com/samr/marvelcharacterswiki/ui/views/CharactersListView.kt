package com.samr.marvelcharacterswiki.ui.views

import com.samr.marvelcharacterswiki.models.CharacterModel

interface CharactersListView {
    fun onCharacterslistReceived(characters: List<CharacterModel>)
    fun onError(errorMessage: String)
}