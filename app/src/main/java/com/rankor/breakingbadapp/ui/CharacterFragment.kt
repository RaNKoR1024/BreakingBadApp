package com.rankor.breakingbadapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.rankor.breakingbadapp.R
import com.rankor.breakingbadapp.databinding.FragmentCharacterBinding
import com.rankor.breakingbadapp.domain.Category.BB
import com.rankor.breakingbadapp.domain.Category.BB_AND_BCS
import com.rankor.breakingbadapp.domain.Category.BCS
import com.rankor.breakingbadapp.ui.entities.UiIntent
import com.rankor.breakingbadapp.ui.entities.UiState


class CharacterFragment : BaseFragment(R.layout.fragment_character) {

    private val binding
        get() = _binding!! as FragmentCharacterBinding

    override fun bindView(view: View): ViewBinding = FragmentCharacterBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val color = ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
            ctlCharacter.setCollapsedTitleTextColor(color)
            ctlCharacter.setExpandedTitleColor(color)
            tbName.setTitleTextColor(color)
            tbName.setNavigationOnClickListener {
                viewModel.dispatch(UiIntent.GetBBCharacters)
            }
        }
    }

    override fun render(state: UiState) {
        when (state) {
            is UiState.BBCharacterState -> renderThisState(state)
            is UiState.BBListState -> {
                navController.popBackStack()
            }
        }
    }

    private fun renderThisState(state: UiState.BBCharacterState) {
        with(binding) {
            with(state.bbCharacter) {
                ctlCharacter.title = name
                tbName.title = name
                Glide.with(requireContext())
                    .load(img)
                    .override(400, 400)
                    .placeholder(R.drawable.ic_baseline_image)
                    .error(R.drawable.ic_baseline_error_outline)
                    .into(ivCharacter)
                with(characterInfo) {
                    tvNickname.text = nickname
                    tvBornDate.text = birthday
                    tvStatus.text = status
                }
                characterOccupation.tvOccupation.text = occupation
                with(characterAppearance) {
                    when (category) {
                        BB -> {
                            tvAppearance2Name.visibility = View.GONE
                            tvAppearance2.visibility = View.GONE
                            tvAppearance1Name.text = category
                            tvAppearance1.text = appearance
                        }
                        BCS -> {
                            tvAppearance2Name.visibility = View.GONE
                            tvAppearance2.visibility = View.GONE
                            tvAppearance1Name.text = category
                            tvAppearance1.text = betterCallSaulAppearance
                        }
                        BB_AND_BCS -> {
                            tvAppearance1Name.text = BB
                            tvAppearance1.text = appearance
                            tvAppearance2Name.text = BCS
                            tvAppearance2.text = betterCallSaulAppearance
                        }
                    }
                }
                with(characterActor) {
                    tvActorName.text = portrayed
                    Glide.with(requireContext())
                        .load(img)
                        .override(100, 100)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_image)
                        .error(R.drawable.ic_baseline_error_outline)
                        .into(ivActorAvatar)
                }
            }
        }
    }


}