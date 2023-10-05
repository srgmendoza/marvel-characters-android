package com.sm.feature_listing.data.repo

import com.sm.feature_listing.domain.models.CharacterDomain

interface CharactersListRemoteRepository {
    suspend fun fetchCharactersList(offsetFactor: Int): List<CharacterDomain>
}