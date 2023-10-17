package com.samr.marvelcharacterswiki.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.samr.marvelcharacterswiki.R


@Composable
fun AppBar(state: MutableState<Boolean>,
           modifier: Modifier,
           onGoBackClick: () -> Unit) {

    AnimatedVisibility(visible = state.value) {
        TopAppBar(
            modifier = modifier
        ) {
            IconButton(onClick = onGoBackClick) {
                Icon(painterResource(R.drawable.ic_close), contentDescription = null)
            }
        }
    }
}