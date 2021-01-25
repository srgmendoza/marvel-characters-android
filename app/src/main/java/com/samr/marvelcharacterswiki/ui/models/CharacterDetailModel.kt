package com.samr.marvelcharacterswiki.ui.models

data class CharacterDetailModel(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: DetailThumbnail,
    val storiesCount: Int,
    val comicsCount: Int,
    val seriesCount: Int,
    val eventsCount: Int)

data class DetailThumbnail (
    val path: String,
    val extension: String
)

