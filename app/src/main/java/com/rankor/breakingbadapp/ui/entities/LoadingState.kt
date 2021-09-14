package com.rankor.breakingbadapp.ui.entities

import android.view.View
import com.rankor.breakingbadapp.databinding.ViewLoadingBinding

// class for changing state of loading screen
enum class LoadingState {
    LOADING,
    READY,
    ERROR
}

fun ViewLoadingBinding.setLoadingState(loadingState: LoadingState) {
    with(this) {
        when (loadingState) {
            LoadingState.LOADING -> {
                root.visibility = View.VISIBLE
                pbPreloader.visibility = View.VISIBLE
                btnRetry.visibility = View.GONE
                tvErrorName.visibility = View.GONE
            }
            LoadingState.ERROR -> {
                root.visibility = View.VISIBLE
                pbPreloader.visibility = View.GONE
                btnRetry.visibility = View.VISIBLE
                tvErrorName.visibility = View.VISIBLE
            }
            LoadingState.READY -> {
                root.visibility = View.GONE
                pbPreloader.visibility = View.GONE
                btnRetry.visibility = View.GONE
                tvErrorName.visibility = View.GONE
            }
        }
    }
}