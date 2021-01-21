package com.samr.marvelcharacterswiki.ui

import com.samr.domain.entities.CharacterUI

interface MainView {
    fun onCharacterslistReceived(characters: List<CharacterUI>)
    fun onError(errorMessage: String)
}