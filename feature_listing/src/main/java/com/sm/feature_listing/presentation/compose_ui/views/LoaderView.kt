package com.sm.feature_listing.presentation.compose_ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun LoaderView() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.DarkGray) {
        CircularProgressIndicator(
            modifier = androidx.compose.ui.Modifier
                .width(64.dp)
        )
    }
}