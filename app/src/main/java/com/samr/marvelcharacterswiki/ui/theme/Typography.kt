package com.samr.marvelcharacterswiki.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    h4 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 32.sp,
        letterSpacing = 24.sp,
        textAlign = TextAlign.Center
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        shadow = Shadow(
            color = colorAccentTheme1,
            offset = Offset(4f, 4f),
            blurRadius = 8f
        )
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 8.sp,
        shadow = Shadow(
            color = Color.Black,
            offset = Offset(2f, 2f),
            blurRadius = 1f
        )
    ),
)