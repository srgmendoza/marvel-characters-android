package com.samr.marvelcharacterswiki.ui.compose_views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import com.samr.marvelcharacterswiki.R

@Composable
fun AppBar(state: MutableState<Boolean>, onGoBackClick: () -> Unit) {

    AnimatedVisibility(visible = state.value) {
        IconButton(onClick = onGoBackClick) {
            Icon(painterResource(R.drawable.ic_close), contentDescription = null)
        }
    }
}