package com.stefita.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharactersSource(
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
) : Parcelable {

    val occupations
        get() = occupation.joinToString { it }

    val appearances
        get() = appearance?.joinToString { "Season $it" } ?: ""
}