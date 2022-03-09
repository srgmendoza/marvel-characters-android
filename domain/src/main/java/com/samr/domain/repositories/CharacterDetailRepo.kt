package com.samr.domain.repositories

import com.samr.data.utils.LayerResult
import com.samr.domain.models.Character

interface CharacterDetailRepo {

    suspend fun fetchCharacterDetail(characterId: String, callback: (com.samr.data.utils.LayerResult<Character>?) -> Unit)
}
