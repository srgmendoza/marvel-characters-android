package com.samr.marvelcharacterswiki.ui.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.samr.marvelcharacterswiki.R

enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {
    LISTING(R.string.listing_tab_title, R.drawable.ic_home, "listing"),
    SEARCH(R.string.search_tab_title, R.drawable.ic_search, "search")
}