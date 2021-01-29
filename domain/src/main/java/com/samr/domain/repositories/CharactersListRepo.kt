package com.samr.domain.repositories

import com.samr.core.utils.LayerResult
import com.samr.domain.entities.CharacterEntity

interface CharactersListRepo {

    suspend fun fetchCharactersList(offsetFactor: Int,
                            callback: (LayerResult<List<CharacterEntity>>?) -> Unit)
}