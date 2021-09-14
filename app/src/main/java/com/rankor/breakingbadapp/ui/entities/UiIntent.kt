package com.rankor.breakingbadapp.ui.entities

// class for intents which send by views on user actions
sealed class UiIntent {

    object GetBBCharacters : UiIntent()

    object GoToBBCharacter : UiIntent() {
        var charId: Int = 1
    }

}
