package com.sm.listing.data.repo

import android.annotation.SuppressLint
import android.util.Log
import com.sm.listing.data.ListingEndpoints
import com.sm.listing.domain.models.Character
import com.sm.listing.domain.repositories.CharacterRemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.security.MessageDigest

const val CHARACTERS_ENDPOINT = "characters"

const val DATA_LIMIT = "40"

const val PRIVATE_KEY = "342f2e0f995a9cffd4cda17b1b9e1f61e6c55da0"
const val PUBLIC_KEY = "9e37537e8b60f83fc1cb829de89cccb8"
const val TIMESTAMP = "timestamp"
const val HASH = "hash"

class ListingRepository(private val listingEndpoints: ListingEndpoints) : CharacterRemoteRepository {

    @SuppressLint("CheckResult")
    override fun fetchCharactersList(
        offsetFactor: Int,
        onCharactersReceived: (Result<List<Character>>) -> Unit
    ) {
        val timeStampPlusHash = getTimeStampPlusHash()

        listingEndpoints.getCharacters(
            offset = offsetFactor.getOffset().toString(),
            hash = timeStampPlusHash[HASH] ?: "",
            ts = timeStampPlusHash[TIMESTAMP].toString()
        )
            .map { raw ->
                raw.mapToData().map { entity ->
                    entity.mapDataToEntity()
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { model ->
                    onCharactersReceived(Result.success(model))
                },
                {
                    /*                   val error = CustomError(
                                           originLayer = CustomError.OriginLayer.DATA_LAYER,
                                           underLyingError = it
                                       )*/
                    onCharactersReceived(Result.failure(it))
                }
            )
    }

    private fun getTimeStampPlusHash(): Map<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()

        val timestamp = getTimeStamp()

        result[TIMESTAMP] = timestamp
        result[HASH] = md5(timestamp + PRIVATE_KEY + PUBLIC_KEY).toHex()

        return result
    }

    private fun getTimeStamp() = (System.currentTimeMillis() / 1000).toString()

    private fun Int.getOffset() = (if (this == 0) 40 else this * 40)

    private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(
        str.toByteArray(
            Charsets.UTF_8
        )
    )

    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }


}