package com.stefita.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.stefita.domain.entities.CharacterEntity

@Entity(tableName = "characters")
data class CharacterData(
    @PrimaryKey
    @SerializedName("char_id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("birthday") var birthday: String,
    @SerializedName("occupation") var occupation: List<String>,
    @SerializedName("img") var img: String,
    @SerializedName("status") var status: String,
    @SerializedName("nickname") var nickname: String,
    @SerializedName("appearance") var appearance: List<Int>,
    @SerializedName("portrayed") var portrayed: String,
    @SerializedName("category") var category: String,
    @SerializedName("betterCallSaulAppearance") var betterCallSaulAppearance: List<Int>
)

class CharacterDataEntityMapper constructor() {

    fun mapCharacterToEntity(data: CharacterData): CharacterEntity = CharacterEntity(
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

class CharacterEntityDataMapper constructor() {

    fun mapCharacterToData(response: CharacterEntity): CharacterData = CharacterData(
        id = response.id,
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