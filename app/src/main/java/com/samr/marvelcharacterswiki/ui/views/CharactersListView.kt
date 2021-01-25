package com.samr.marvelcharacterswiki.ui.views

import com.samr.marvelcharacterswiki.ui.fragments.CharactersListFragment
import com.samr.marvelcharacterswiki.ui.models.CharacterModel

interface CharactersListView {
    fun onCharacterslistReceived(characters: List<CharacterModel>)
    fun onError(errorMessage: String)
}