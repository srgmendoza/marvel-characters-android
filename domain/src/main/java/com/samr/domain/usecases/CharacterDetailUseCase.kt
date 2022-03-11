package com.samr.domain.usecases

import com.samr.domain.repositories.CharacterLocalRepository

class CharacterDetailUseCase(private val localRepo: CharacterLocalRepository) {

    fun getCharacterById(id: String) = localRepo.selectById(id)
}
