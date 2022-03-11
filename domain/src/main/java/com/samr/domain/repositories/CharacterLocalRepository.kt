package com.samr.domain.repositories

import androidx.lifecycle.LiveData
import com.samr.domain.models.Character

interface CharacterLocalRepository {
    fun insert(characterEntity: Character,
               onCharacterSaved: (Result<Boolean>) -> Unit)
    fun selectAll(): LiveData<List<Character>>
    fun selectById(characterId: String): LiveData<Character>
    fun deleteAll(onCharactersDeleted: (Result<Boolean>) -> Unit)
}