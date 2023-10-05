package com.sm.feature_listing.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samr.domain.models.CustomErrorQ
import com.sm.feature_listing.data.paging.ListingPagingHelper
import com.sm.feature_listing.domain.models.CharacterDomain
import com.sm.feature_listing.domain.repositories.CharacterRemoteRepository
import kotlinx.coroutines.flow.Flow

class CharacterListUsecase(private val remoteRepo: CharacterRemoteRepository
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
