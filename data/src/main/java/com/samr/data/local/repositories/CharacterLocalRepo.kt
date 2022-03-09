package com.samr.data.local.repositories

import android.annotation.SuppressLint
import com.samr.data.entities.CharacterEntity
import com.samr.data.local.Database
import com.samr.data.entities.CustomError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterLocalRepo(private val database: Database) {

    @SuppressLint("CheckResult")
    fun insert(characterEntity: CharacterEntity,
               onCharacterSaved: (Result<Boolean>) -> Unit) {
        database.getCharactersDao().insert(characterEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                onCharacterSaved(Result.success(true))
            }, {
                val error = CustomError(it, CustomError.OriginLayer.DATA_LAYER)
                onCharacterSaved(Result.failure(error))
            })
    }

    fun selectAll() = database.getCharactersDao().getAll()

    fun selectById(characterId: String) = database.getCharactersDao().getCharacterById(characterId)

    @SuppressLint("CheckResult")
    fun deleteAll(onCharactersDeleted: (Result<Boolean>) -> Unit) {
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