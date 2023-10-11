package com.sm.feature_detail.presentation.models

data class CharacterDetail(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String, //http://i.annihil.us/u/prod/marvel/i/mg/8/a0/523ca6f2b11e4/standard_amazing.jpg
    val moreContentUrl: String
)

fun getExampleCharacter() = CharacterDetail(
    id = 1234567890,
    name = "A Pretty cool hero",
    description = "description",
    imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/8/a0/523ca6f2b11e4/standard_amazing.jpg",
    moreContentUrl = ""
)

