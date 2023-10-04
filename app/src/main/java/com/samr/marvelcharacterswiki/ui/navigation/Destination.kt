package com.samr.marvelcharacterswiki.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val name: String, val path: String, val icon: ImageVector) {
    object Listing : Destination(name = "Listing", path = "listing", icon = Icons.Default.List)
}
