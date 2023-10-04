package com.samr.marvelcharacterswiki.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    isDarkThemeStore: Boolean = isSystemInDarkTheme(),
    themeId: Int,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isDarkThemeStore) DarkColors else LightColors,
        typography = AppTypography ,
        shapes = AppShapes,
        content = content
    )
}

private val LightColors = lightColors(
    primary = colorPrimaryTheme2,
    primaryVariant = colorPrimaryDarkTheme2,
    onPrimary = Color.White,
    secondary = colorAccentTheme2,
    secondaryVariant = colorAccentTheme2,
    onSecondary = Color.White,
    error = Color.Red,
    onBackground = Color.Black
)

private val DarkColors = darkColors(
    primary = colorPrimaryTheme2,
    primaryVariant = colorPrimaryDarkTheme2,
    onPrimary = Color.White,
    secondary = colorAccentTheme2,
    secondaryVariant = colorAccentTheme2,
    onSecondary = Color.Black,
    error = Color.Red,
    onBackground = Color.White
)