package com.rankor.breakingbadapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.rankor.breakingbadapp.ui.entities.UiState

abstract class BaseFragment(@LayoutRes val contentLayoutId: Int): Fragment() {

    protected lateinit var viewModel: MainViewModel
    protected var _binding: ViewBinding? = null
    protected lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(contentLayoutId, container, false)
        _binding = bindView(view)
        return _binding!!.root
    }

    abstract fun bindView(view: View): ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = findNavController()
        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    abstract fun render(state: UiState)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}