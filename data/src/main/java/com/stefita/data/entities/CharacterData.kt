package com.stefita.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.stefita.domain.entities.CharacterEntity

@Entity(tableName = "characters")
data class CharacterData(
    @PrimaryKey
    var char_id: Int,
    var name: String,
    var birthday: String,
    var occupation: List<String>,
    var img: String,
    var status: String,
    var nickname: String,
    var appearance: List<Int>,
    var portrayed: String,
    var category: String,
    var betterCallSaulAppearance: List<Int>? = emptyList()
)

class CharacterDataEntityMapper constructor() {

    fun mapCharacterToEntity(data: CharacterData): CharacterEntity = CharacterEntity(
        id = data.char_id,
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

class CharacterEntityDataMapper  {

    fun mapCharacterToData(response: CharacterEntity): CharacterData = CharacterData(
        char_id = response.id,
        name = response.name,
        birthday = response.birthday,
        occupation = response.occupation,
        img = response.img,
        status = response.status,
        nickname = response.nickname,
        appearance = response.appearance,
        portrayed = response.portrayed,
        category = response.category,
        betterCallSaulAppearance = response.betterCallSaulAppearance
    )
}