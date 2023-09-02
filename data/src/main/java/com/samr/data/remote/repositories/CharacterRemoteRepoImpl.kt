package com.samr.data.remote.repositories

import android.annotation.SuppressLint
import com.samr.domain.models.CustomErrorA
import com.samr.data.remote.endpoints.CharacterEndpoints
import com.samr.data.utils.Utils.HASH
import com.samr.data.utils.Utils.TIMESTAMP
import com.samr.data.utils.Utils.getOffset
import com.samr.data.utils.Utils.getTimeStampPlusHash
import com.samr.domain.models.Character
import com.samr.domain.repositories.CharacterRemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterRemoteRepoImpl(private val endpoints: CharacterEndpoints): CharacterRemoteRepository {

    @SuppressLint("CheckResult")
    override fun fetchCharactersList(offsetFactor: Int,
                                     onCharactersReceived: (Result<List<Character>>) -> Unit) {

        val timeStampPlusHash = getTimeStampPlusHash()

        endpoints.getCharacters(
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
                    val error = CustomErrorA(
                        originLayer = CustomErrorA.OriginLayerA.DATA_LAYER,
                        underLyingError = it
                    )
                    onCharactersReceived(Result.failure(error))
                }
            )
    }
}
