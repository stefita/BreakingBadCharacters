package com.stefita.domain.entities

data class CharacterEntity(
    val id: Int = 0,
    val name: String = "",
    val birthday: String = "",
    val occupation: List<String> = emptyList(),
    val img: String = "",
    val status: String = "",
    val nickname: String = "",
    val appearance: List<Int>? = emptyList(),
    val portrayed: String = "",
    val category: String = "",
    val betterCallSaulAppearance: List<Int>? = emptyList()
)