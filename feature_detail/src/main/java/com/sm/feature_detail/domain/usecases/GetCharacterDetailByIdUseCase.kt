package com.sm.feature_detail.domain.usecases

import com.sm.feature_detail.domain.models.CharacterDomain

interface GetCharacterDetailByIdUseCase {
    suspend fun execute(id: String): CharacterDomain?
}