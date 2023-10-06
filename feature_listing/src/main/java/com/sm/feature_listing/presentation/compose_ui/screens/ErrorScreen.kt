 package com.sm.feature_listing.presentation.compose_ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ErrorScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Oops, an error happened!! \nShould retry?",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Button(onClick = { navHostController.popBackStack() }) {
                Text(text = "Retry")
            }
        }

    }
}
