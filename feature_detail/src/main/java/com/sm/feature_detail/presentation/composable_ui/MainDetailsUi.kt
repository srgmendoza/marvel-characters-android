package com.sm.feature_detail.presentation.composable_ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainDetailsUi(navController: NavHostController) {
    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    Text(text = "Welcome to DetailsUi for character with id: $id")
}