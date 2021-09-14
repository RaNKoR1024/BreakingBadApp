package com.rankor.breakingbadapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rankor.breakingbadapp.R
import com.rankor.breakingbadapp.domain.BBApp
import com.rankor.breakingbadapp.domain.SingleEvent
import com.rankor.breakingbadapp.ui.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val bbApp = application as BBApp
    private val intentChannel = Channel<UiIntent>(Channel.UNLIMITED)
    private var _state = MutableLiveData<UiState>()
    val state: LiveData<UiState>
        get() = _state
    private val _singleEvent = MutableLiveData<SingleEvent<UiEvent>>()
    val singleEvent: LiveData<SingleEvent<UiEvent>>
        get() = _singleEvent

    private var characterList = emptyList<BBCharacterItem>()

    init {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                reduce(it)
            }
        }
    }

    fun dispatch(intent: UiIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            intentChannel.send(intent)
        }
    }

    private suspend fun reduce(intent: UiIntent) {
        when (intent) {
            is UiIntent.GetBBCharacters -> withContext(Dispatchers.IO) {
                getBBList()
            }
            is UiIntent.GoToBBCharacter -> withContext(Dispatchers.IO) {
                getBBCharacter(intent.charId)
            }
        }
    }

    private suspend fun getBBList() {
        if (characterList.isNotEmpty()) {
            _state.postValue(
                UiState.BBListState(
                    listBBCharacterResponse = characterList,
                    loadingState = LoadingState.READY
                )
            )
            return
        }
        kotlin.runCatching {
            _state.postValue(
                UiState.BBListState(
                    loadingState = LoadingState.LOADING
                )
            )
            val charList = bbApp.bbApi.getBBList()
            characterList = List(charList.size) { i -> charList[i].parseToCharacterItem() }
            _state.postValue(
                UiState.BBListState(
                    listBBCharacterResponse = characterList,
                    loadingState = LoadingState.READY
                )
            )
        }.onFailure {
            _state.postValue(
                UiState.BBListState(
                    loadingState = LoadingState.ERROR
                )
            )
        }
    }

    private suspend fun getBBCharacter(charId: Int) {
        kotlin.runCatching {
            _state.postValue(
                UiState.BBListState(
                    loadingState = LoadingState.LOADING
                )
            )
            val character = bbApp.bbApi.getBBCharacter(charId)
            _state.postValue(
                UiState.BBCharacterState(
                    bbCharacter = character.first().parseToCharacter(bbApp)
                )
            )
        }.onFailure {
            _state.postValue(
                UiState.BBListState(
                    loadingState = LoadingState.READY
                )
            )
            singleEvent(
                UiEvent.ShortToast(R.string.connection_error)
            )
        }
    }

    private fun singleEvent(event: UiEvent) {
        _singleEvent.postValue(SingleEvent(event))
    }
}