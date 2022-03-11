package com.samr.domain.repositories

import com.samr.domain.models.Character

interface CharacterRemoteRepository {
    fun fetchCharactersList(offsetFactor: Int,
                            onCharactersReceived: (Result<List<Character>>) -> Unit)
}