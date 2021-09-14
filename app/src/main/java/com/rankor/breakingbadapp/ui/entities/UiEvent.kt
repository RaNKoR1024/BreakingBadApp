package com.rankor.breakingbadapp.ui.entities

import androidx.annotation.StringRes

sealed class UiEvent {
    class ShortToast(@StringRes val messageId: Int): UiEvent()
}
