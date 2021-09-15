package com.rankor.breakingbadapp.ui.entities

import com.rankor.breakingbadapp.ui.common.LoadingState
import com.rankor.breakingbadapp.ui.common.character.Character
import com.rankor.breakingbadapp.ui.common.listcharacters.CharacterItem

// class for changing state of fragment
sealed class UiState {

    class BBListState(
        val listCharacterResponse: List<CharacterItem> = emptyList(),
        val loadingState: LoadingState = LoadingState.LOADING
    ) : UiState()

    class BBCharacterState(val character: Character) : UiState()

}

