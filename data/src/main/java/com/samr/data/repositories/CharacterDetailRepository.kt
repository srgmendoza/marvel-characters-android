package com.samr.data.repositories

import com.samr.core.utils.LayerResult
import com.samr.data.entities.CharacterData

interface CharacterDetailRepository {

    fun fetchCharacterDetail(characterId: String, callback: (LayerResult<CharacterData>?) -> Unit)
}