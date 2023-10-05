package com.sm.feature_listing.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sm.feature_listing.data.paging.ListingPagingHelper
import com.sm.feature_listing.domain.models.CharacterDomain
import com.sm.feature_listing.data.repo.CharactersListRemoteRepository
import kotlinx.coroutines.flow.Flow

class GetPagedCharactersListUseCase(
    private val remoteRepo: CharactersListRemoteRepository
) {
    fun execute(): Flow<PagingData<CharacterDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 40
            ),
            pagingSourceFactory = {
                ListingPagingHelper(remoteRepo)
            }
        ).flow
    }
}
