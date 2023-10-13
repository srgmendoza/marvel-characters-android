package com.sm.core_navigation.models

import com.sm.core_navigation.CoreNavigation

data class NavigationConfig(
    val startDestinationRoute: String,
    val destinations: List<Pair<Boolean, CoreNavigation>>
)
