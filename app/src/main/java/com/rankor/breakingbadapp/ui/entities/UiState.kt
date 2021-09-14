package com.rankor.breakingbadapp.ui.entities

// class for changing state of fragment
sealed class UiState {

    class BBListState(
        val listBBCharacterResponse: List<BBCharacterItem> = emptyList(),
        val loadingState: LoadingState = LoadingState.LOADING
    ) : UiState()

    class BBCharacterState(val bbCharacter: BBCharacter) : UiState()

}

