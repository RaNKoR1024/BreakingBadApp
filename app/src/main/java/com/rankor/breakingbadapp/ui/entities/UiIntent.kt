package com.rankor.breakingbadapp.ui.entities

sealed class UiIntent {

    object GetBBCharacters: UiIntent()
    object GoToBBCharacter: UiIntent() {
        var charId: Int = 1
    }
}
