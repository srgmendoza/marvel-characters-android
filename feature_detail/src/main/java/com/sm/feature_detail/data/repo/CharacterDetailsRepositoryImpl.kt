package com.sm.feature_detail.data.repo

import com.example.core_utils.HASH
import com.example.core_utils.TIMESTAMP
import com.example.core_utils.getTimeStampPlusHash
import com.sm.feature_detail.data.endpoints.DetailsEndpoint
import com.sm.feature_detail.data.entities.CharacterEntity

const val DATA_LIMIT = "1"
private const val PRIVATE_KEY = "342f2e0f995a9cffd4cda17b1b9e1f61e6c55da0"
const val PUBLIC_KEY = "9e37537e8b60f83fc1cb829de89cccb8"

class CharacterDetailsRepositoryImpl(private val endpoint: DetailsEndpoint): CharacterDetailsRepository {
    override suspend fun fetchCharacterDetails(id: String): CharacterEntity? {
        val timeStampPlusHash = getTimeStampPlusHash(
            publicKey = PUBLIC_KEY,
            privateKey = PRIVATE_KEY
        )

        return endpoint.getCharacterDetailsById(
            character = id,
            hash = timeStampPlusHash[HASH] ?: "",
            ts = timeStampPlusHash[TIMESTAMP].toString()
        ).mapToData().firstOrNull()
    }
}