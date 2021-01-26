package com.samr.marvelcharacterswiki.models

data class CharacterDetailModel(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: DetailThumbnail,
    val storiesCount: Long,
    val comicsCount: Long,
    val seriesCount: Long,
    val eventsCount: Long)

data class DetailThumbnail (
    val path: String,
    val extension: String
)

