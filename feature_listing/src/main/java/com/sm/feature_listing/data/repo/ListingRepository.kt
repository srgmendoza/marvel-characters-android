package com.sm.feature_listing.data.repo

import com.example.core_utils.HASH
import com.example.core_utils.TIMESTAMP
import com.example.core_utils.getOffset
import com.example.core_utils.getTimeStampPlusHash
import com.sm.feature_listing.data.ListingEndpoints
import com.sm.feature_listing.domain.models.CharacterDomain
import com.sm.feature_listing.domain.repositories.CharacterRemoteRepository

const val CHARACTERS_ENDPOINT = "characters"

const val DATA_LIMIT = "40"
private const val PRIVATE_KEY = "342f2e0f995a9cffd4cda17b1b9e1f61e6c55da0"
const val PUBLIC_KEY = "9e37537e8b60f83fc1cb829de89cccb8"



class ListingRepository(private val listingEndpoints: ListingEndpoints) : CharacterRemoteRepository {

    override suspend fun fetchCharactersList(offsetFactor: Int): List<CharacterDomain> {
        val timeStampPlusHash = getTimeStampPlusHash(
            publicKey = PUBLIC_KEY,
            privateKey = PRIVATE_KEY
        )

        return listingEndpoints.getCharacters(
            offset = offsetFactor.getOffset().toString(),
            hash = timeStampPlusHash[HASH] ?: "",
            ts = timeStampPlusHash[TIMESTAMP].toString()
        ).mapToData().map {
            it.mapDataToEntity()
        }
    }
}