package com.stefita.presentation.mapper

import com.stefita.domain.common.Mapper
import com.stefita.domain.entities.CharacterEntity
import com.stefita.presentation.entities.CharactersSource

class CharactersEntityMapper : Mapper<CharacterEntity, CharactersSource>() {

    override fun mapFrom(data: CharacterEntity): CharactersSource = CharactersSource(
        id = data.id,
        name = data.name,
        birthday = data.birthday,
        occupation = data.occupation,
        img = data.img,
        status = data.status,
        nickname = data.nickname,
        appearance = data.appearance,
        portrayed = data.portrayed,
        category = data.category,
        betterCallSaulAppearance = data.betterCallSaulAppearance
    )
}