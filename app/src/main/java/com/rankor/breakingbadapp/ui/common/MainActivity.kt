package com.rankor.breakingbadapp.ui.common

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rankor.breakingbadapp.R
import com.rankor.breakingbadapp.databinding.ActivityMainBinding
import com.rankor.breakingbadapp.ui.entities.UiEvent
import com.rankor.breakingbadapp.ui.entities.UiIntent
import com.rankor.breakingbadapp.ui.entities.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.state.observe(this) { render(it) }
        viewModel.singleEvent.observe(this) {
            it.getContentIfNotHandled()?.let { event -> renderSingleEvent(event) }
        }
    }

    // to react on state
    private fun render(state: UiState) {
        when (state) {
            is UiState.BBListState -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            }
            is UiState.BBCharacterState -> {
                lifecycleScope.launch {
                    delay(300L)
                    window.statusBarColor =
                        ContextCompat.getColor(this@MainActivity, R.color.transparent)
                }
            }
        }
    }

    // to process single event
    private fun renderSingleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ShortToast -> {
                Toast.makeText(this, event.messageId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // fixes bug when user can't exit CharacterFragment by pressing back button
    override fun onBackPressed() {
        if (viewModel.state.value is UiState.BBCharacterState) {
            viewModel.dispatch(UiIntent.GetBBCharacters)
            return
        }
        super.onBackPressed()
    }
}