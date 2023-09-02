package com.sm.listing.domain.repositories

import com.sm.listing.domain.models.Character

interface CharacterRemoteRepository {
    fun fetchCharactersList(offsetFactor: Int,
                            onCharactersReceived: (Result<List<Character>>) -> Unit)
}