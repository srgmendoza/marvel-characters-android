package com.sm.listing.ui.models

data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Images,
)

data class Images(
    val thumbnail: String,
    val poster: String
)
