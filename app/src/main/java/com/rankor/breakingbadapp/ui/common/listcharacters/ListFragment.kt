package com.rankor.breakingbadapp.ui.common.listcharacters

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.rankor.breakingbadapp.R
import com.rankor.breakingbadapp.databinding.FragmentListBinding
import com.rankor.breakingbadapp.ui.common.BaseFragment
import com.rankor.breakingbadapp.ui.common.LoadingState
import com.rankor.breakingbadapp.ui.common.setLoadingState
import com.rankor.breakingbadapp.ui.entities.UiIntent
import com.rankor.breakingbadapp.ui.entities.UiState

class ListFragment : BaseFragment(R.layout.fragment_list) {

    private val binding
        get() = _binding!! as FragmentListBinding
    private lateinit var adapter: CharacterAdapter

    override fun bindView(view: View): ViewBinding = FragmentListBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CharacterAdapter(requireContext()) {
            viewModel.dispatch(UiIntent.GoToBBCharacter.apply {
                charId = it.charId
            })
        }
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager = LinearLayoutManager(context)
        binding.loadingScreen.btnRetry.setOnClickListener {
            viewModel.dispatch(UiIntent.GetBBCharacters)
        }
        viewModel.dispatch(UiIntent.GetBBCharacters)
    }

    override fun render(state: UiState) {
        when (state) {
            is UiState.BBListState -> renderThisState(state)
            is UiState.BBCharacterState -> {
                navController.navigate(R.id.action_listFragment_to_characterFragment)
            }
        }
    }

    private fun renderThisState(state: UiState.BBListState) {
        with(state) {
            binding.loadingScreen.setLoadingState(loadingState)
            adapter.isClickAllowed = loadingState == LoadingState.READY
            if (listCharacterResponse.isNotEmpty()) {
                adapter.setData(listCharacterResponse)
            }
        }
    }
}