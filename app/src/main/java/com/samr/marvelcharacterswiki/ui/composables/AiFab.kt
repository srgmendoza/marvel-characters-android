package com.samr.marvelcharacterswiki.ui.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.samr.marvelcharacterswiki.R

@Composable
fun AiFab(navController: NavController) {
    FloatingActionButton(
        onClick = {},
    ) {
        Icon(painterResource(R.drawable.ic_brain), contentDescription = "IA Search")
    }
}