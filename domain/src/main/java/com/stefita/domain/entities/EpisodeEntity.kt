package com.stefita.domain.entities

data class EpisodeEntity(
    val id: Int,
    val title: String,
    val season: Int,
    val episode: Int,
    val airDate: String,
    val characters: List<CharacterEntity>,
    val series: String
)