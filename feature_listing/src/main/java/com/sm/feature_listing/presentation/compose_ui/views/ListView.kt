package com.sm.feature_listing.presentation.compose_ui.views

import android.util.Log
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.sm.feature_listing.presentation.models.ListedCharacter
import kotlinx.coroutines.flow.Flow

@Composable
fun ListView(characters: Flow<PagingData<ListedCharacter>>,
             onClick: (ListedCharacter) -> Unit,
             onError: () -> Unit) {
    val lazyPagingItems = characters.collectAsLazyPagingItems()
    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
        items(count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey(),
            contentType = lazyPagingItems.itemContentType()) {
            val item = lazyPagingItems[it]
            if (item != null) {
                ListedItemView(item = item, onClick)
            }
        }

        //Error handling
        lazyPagingItems.apply {
            val error = when {
                loadState.refresh is LoadState.Error -> {
                    Log.e("ListView", "Error refresh")
                    loadState.refresh as? LoadState.Error
                }
                loadState.append is LoadState.Error -> {
                    Log.e("ListView", "Error append")
                    loadState.append as? LoadState.Error
                }
                else -> null
            }

            if (error != null) {
                onError()
            }
        }
    }
}