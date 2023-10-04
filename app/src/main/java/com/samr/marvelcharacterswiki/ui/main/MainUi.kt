package com.samr.marvelcharacterswiki.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.samr.marvelcharacterswiki.ui.theme.ThemeStore
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.samr.marvelcharacterswiki.ui.compose_views.BottomBar
import com.samr.marvelcharacterswiki.ui.models.BottomTabs
import com.samr.marvelcharacterswiki.ui.navigation.Destination
import com.samr.marvelcharacterswiki.ui.theme.AppTheme

@Composable
fun MainUi(themeStore: ThemeStore) {
    val theme by themeStore.getTheme().collectAsState(initial = 0) //Initialize with default theme 0
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()


    AppTheme(themeId = theme) {
        val statusBarColor = MaterialTheme.colors.primaryVariant
        val tabs = remember { BottomTabs.values() }

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = statusBarColor,
                darkIcons = false
            )
        }

        Scaffold(
            bottomBar = { BottomBar(navController = navController, tabs) }
        ) { innerPadding ->
            MainNavigationUi(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }

    }
}