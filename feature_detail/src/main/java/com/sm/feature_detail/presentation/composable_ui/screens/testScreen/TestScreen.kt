package com.sm.feature_detail.presentation.composable_ui.screens.testScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TestScreen() {
    Box {
        Text(text = "This is a Screen to test internal navigation inside Details feature")
    }
}