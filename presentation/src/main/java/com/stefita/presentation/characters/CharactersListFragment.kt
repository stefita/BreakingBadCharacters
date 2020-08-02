package com.stefita.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stefita.presentation.characters.CharactersViewModel.ListState.*
import com.stefita.presentation.common.extensions.observe
import com.stefita.presentation.databinding.CharactersListFragmentBinding
import com.stefita.presentation.entities.CharactersSource
import org.koin.android.viewmodel.ext.android.viewModel


class CharactersListFragment : Fragment() {

    private lateinit var binding: CharactersListFragmentBinding
    private lateinit var listAdapter: CharactersListAdapter
    private val viewModel by viewModel<CharactersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharactersListFragmentBinding.inflate(layoutInflater)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            listAdapter = CharactersListAdapter(::viewCharacterDetails)
            adapter = listAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (val charState = state!!) {
                is Loading -> {
                }
                is Empty -> {
                }
                is Success -> {
                    listAdapter.updateList(charState.characters)
                }
            }
        }
    }

    private fun viewCharacterDetails(character: CharactersSource) {
        val action = CharactersListFragmentDirections.toDetailesViewr(character)
        findNavController().navigate(action)
    }
}