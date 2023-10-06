package com.sm.feature_detail.data.repo

import com.sm.feature_detail.data.entities.CharacterEntity

interface CharacterDetailsRepository {
    suspend fun fetchCharacterDetails(id: String): CharacterEntity?
}