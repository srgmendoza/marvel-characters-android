package com.sm.feature_detail.presentation.composable_ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.sm.core_navigation.getArgumentByKey

@Composable
fun MainDetailsUi(navController: NavHostController) {
    val id = navController.getArgumentByKey("id")
    Text(text = "Welcome to DetailsUi for character with id: $id")
}