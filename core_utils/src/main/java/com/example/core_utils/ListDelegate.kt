package com.example.core_utils

interface ListDelegate {
    fun onMoreItemsRequest()
    fun getLoadingState(): Boolean
    fun <T> onItemSelected(item: T)
}