package com.samr.data.repositories

import com.samr.core.utils.LayerResult
import com.samr.data.entities.CharacterData

interface CharactersRepository {

    fun fetchCharactersList(offset: Int,
                            callback: (LayerResult<List<CharacterData>>?) -> Unit)
}