package com.sm.feature_listing.domain.repositories

import com.sm.feature_listing.domain.models.Character

interface CharacterRemoteRepository {
    fun fetchCharactersList(offsetFactor: Int,
                            onCharactersReceived: (Result<List<Character>>) -> Unit)
}