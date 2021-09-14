package com.rankor.breakingbadapp.ui.entities

sealed class UiState {

    class BBListState(
        val listBBCharacterResponse: List<BBCharacterItem> = emptyList(),
        val loadingState: LoadingState = LoadingState.LOADING
    ) : UiState()

    class BBCharacterState(val bbCharacter: BBCharacter) : UiState()
}

