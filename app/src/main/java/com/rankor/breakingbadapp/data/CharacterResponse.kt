package com.rankor.breakingbadapp.data

import android.content.Context
import com.rankor.breakingbadapp.R
import com.rankor.breakingbadapp.domain.Constants
import com.rankor.breakingbadapp.ui.common.character.Character
import com.rankor.breakingbadapp.ui.common.listcharacters.CharacterItem

data class CharacterResponse(
    val char_id: Int,
    val name: String,
    val birthday: String,
    val occupation: List<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val appearance: List<Int>,
    val portrayed: String,
    val category: String,
    val better_call_saul_appearance: List<Int>
) {

    // parsing response to simplified character class
    // for setting information in recycle view items
    fun parseToCharacterItem(): CharacterItem =
        CharacterItem(
            charId = char_id,
            name = name,
            img = img
        )

    // parsing response to special character class for setting string values on view
    fun parseToCharacter(context: Context): Character {
        with(this@CharacterResponse) {
            return Character(
                name = name,
                birthday = parseBornDate(context, birthday),
                occupation = parseOccupation(occupation),
                img = img,
                status = status,
                nickname = nickname,
                appearance = parseListInt(appearance),
                portrayed = portrayed,
                category = category,
                betterCallSaulAppearance = parseListInt(better_call_saul_appearance)
            )
        }
    }

    private fun parseBornDate(context: Context, date: String): String {
        if (date == Constants.UNKNOWN) {
            return "-"
        }
        val day = date.substring(3, 5)
        val month = when (date.substring(0, 2).toInt()) {
            1 -> context.getString(R.string.january)
            2 -> context.getString(R.string.february)
            3 -> context.getString(R.string.march)
            4 -> context.getString(R.string.april)
            5 -> context.getString(R.string.may)
            6 -> context.getString(R.string.june)
            7 -> context.getString(R.string.july)
            8 -> context.getString(R.string.august)
            9 -> context.getString(R.string.september)
            10 -> context.getString(R.string.october)
            11 -> context.getString(R.string.november)
            12 -> context.getString(R.string.december)
            else -> " "
        }
        val year = date.substring(6, 10)
        return "$day $month $year"
    }

    private fun parseOccupation(occupation: List<String>): String {
        var result = ""
        occupation.forEachIndexed { index, i ->
            result += i
            if (index < occupation.size - 1) {
                result += ",\n"
            }
        }
        return result
    }

    private fun parseListInt(list: List<Int>): String {
        var result = ""
        list.forEachIndexed { index, i ->
            result += i.toString()
            if (index < list.size - 1) {
                result += ","
            }
        }
        return result
    }
}
