package com.sm.feature_listing.presentation.compose_ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.sm.feature_listing.presentation.models.ListedCharacter

@Composable
fun ListView(characters: List<ListedCharacter>, onClick: (ListedCharacter) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
        items(characters) {
            ListedItemView(item = it, onClick)
        }
    }
}