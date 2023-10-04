package com.samr.marvelcharacterswiki.ui.compose_views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.samr.marvelcharacterswiki.ui.models.BottomTabs

@Composable
fun BottomBar(navController: NavController, tabs: Array<BottomTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomTabs.LISTING.route

    val routes = remember { BottomTabs.values().map { it.route } }

    if (currentRoute in routes) {
        BottomNavigation(
            Modifier.navigationBarsPadding(),
        ) {
            tabs.forEach { tab ->
                Icon(painterResource(tab.icon), contentDescription = null)
                Box(Modifier.padding(8.dp)) {
                    Text(stringResource(tab.title))
                }
            }
        }
    }
}



