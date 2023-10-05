package com.sm.feature_listing.domain.repositories

import androidx.paging.PagingData
import com.sm.feature_listing.domain.models.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface CharacterRemoteRepository {
    suspend fun fetchCharactersList(offsetFactor: Int): List<CharacterDomain>
}