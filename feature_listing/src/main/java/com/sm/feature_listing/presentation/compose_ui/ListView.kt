package com.sm.feature_listing.presentation.compose_ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.sm.feature_listing.presentation.models.ListedCharacter
import kotlinx.coroutines.flow.Flow

@Composable
fun ListView(characters: Flow<PagingData<ListedCharacter>>, onClick: (ListedCharacter) -> Unit) {
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
    }
}