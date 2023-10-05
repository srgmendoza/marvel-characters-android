package com.sm.core_navigation

import androidx.navigation.NavHostController

fun NavHostController.getArgumentByKey(key: String): String? {
    return currentBackStackEntry?.arguments?.getString(key)
}