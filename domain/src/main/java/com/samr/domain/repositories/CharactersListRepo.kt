package com.samr.domain.repositories

import com.samr.data.utils.LayerResult
import com.samr.domain.models.Character

interface CharactersListRepo {

    suspend fun fetchCharactersList(
        offsetFactor: Int,
        callback: (com.samr.data.utils.LayerResult<List<Character>>?) -> Unit
    )
}
