package com.samr.marvelcharacterswiki.ui.models



data class CharacterModel(
                          val id: Long,
                          val name: String,
                    val thumbnail: Thumbnail)

data class Thumbnail (
        val path: String,
        val extension: String
)
