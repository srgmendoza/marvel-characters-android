package com.samr.data.local.repositories

import android.annotation.SuppressLint
import com.samr.data.local.Database
import com.samr.domain.models.Character
import com.samr.domain.models.CustomError
import com.samr.domain.repositories.CharacterLocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterLocalRepoImpl(private val database: Database): CharacterLocalRepository {

    @SuppressLint("CheckResult")
    override fun insert(character: Character,
               onCharacterSaved: (Result<Boolean>) -> Unit) {

        database.getCharactersDao().insert(character)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                onCharacterSaved(Result.success(true))
            }, {
                val error = CustomError(it, CustomError.OriginLayer.DATA_LAYER)
                onCharacterSaved(Result.failure(error))
            })
    }

    override fun selectAll() = database.getCharactersDao().getAll()

    override fun selectById(characterId: String) = database.getCharactersDao().getCharacterById(characterId)

    @SuppressLint("CheckResult")
    override fun deleteAll(onCharactersDeleted: (Result<Boolean>) -> Unit) {
        database.getCharactersDao().clearAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                onCharactersDeleted(Result.success(true))
            }, {
                val error = CustomError(it, CustomError.OriginLayer.DATA_LAYER)
                onCharactersDeleted(Result.failure(error))
            })
    }
}