package com.sm.feature_detail.domain.usecases

import com.sm.feature_detail.data.repo.CharacterDetailsRepository
import com.sm.feature_detail.domain.models.CharacterDomain
import com.sm.feature_detail.mappers.DataToDomainMapper

class GetCharacterDetailByIdUseCaseImpl(private val repository: CharacterDetailsRepository) :
    GetCharacterDetailByIdUseCase {
    override suspend fun execute(id: String): CharacterDomain? {
        val result = repository.fetchCharacterDetails(id)

        return result?.let { DataToDomainMapper().mapTo(it) }
    }
}