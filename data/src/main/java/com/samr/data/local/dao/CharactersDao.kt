package com.samr.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samr.data.entities.CharacterEntity
import com.samr.domain.models.Character
import io.reactivex.Completable

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: List<Character>): Completable

    @Query("SELECT * FROM Characters ORDER BY internalTS DESC LIMIT :limit")
    fun getAll(limit: Int): LiveData<List<Character>>

    @Query("SELECT * FROM Characters WHERE id = :characterId")
    fun getCharacterById(characterId: String): LiveData<Character>

    @Query("DELETE FROM Characters")
    fun clearAll(): Completable
}