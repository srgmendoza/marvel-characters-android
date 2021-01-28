package com.samr.domain.repositories

import com.samr.core.utils.LayerResult
import com.samr.domain.entities.CharacterEntity

interface CharacterDetailRepo {

    fun fetchCharacterDetail(characterId: String, callback: (LayerResult<CharacterEntity>?) -> Unit)
}