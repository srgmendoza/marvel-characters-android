package com.sm.feature_search.data.repo

import android.annotation.SuppressLint
import com.sm.feature_search.data.api.HASH
import com.sm.feature_search.data.api.PRIVATE_KEY
import com.sm.feature_search.data.api.PUBLIC_KEY
import com.sm.feature_search.data.api.SearchEndpoints
import com.sm.feature_search.data.api.TIMESTAMP
import com.sm.feature_search.domain.models.SearchDM
import com.sm.feature_search.domain.repositories.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.security.MessageDigest

class SearchRepositoryImpl(private val searchEndpoint: SearchEndpoints): SearchRepository {
    @SuppressLint("CheckResult")
    override fun fetchByName(input: String,
                             //offsetFactor: Int,
                             onCharactersReceived: (Result<List<SearchDM>>) -> Unit) {

        val timeStampPlusHash = getTimeStampPlusHash()

        searchEndpoint.searchCharactersByName(
            name = input,
            //offset = offsetFactor.getOffset().toString(),
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