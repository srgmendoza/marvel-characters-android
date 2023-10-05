package com.sm.feature_listing.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sm.feature_listing.domain.models.CharacterDomain
import com.sm.feature_listing.domain.repositories.CharacterRemoteRepository

class ListingPagingHelper(private val remoteRepo: CharacterRemoteRepository) :
    PagingSource<Int, CharacterDomain>() {

    init {
        Log.d("ListingPagingHandler", "init")
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDomain> {
        return try {
            val page = params.key ?: 1

            val response = remoteRepo.fetchCharactersList(page)

            return LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
