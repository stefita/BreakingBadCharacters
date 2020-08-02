package com.stefita.presentation.characters

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stefita.presentation.R
import com.stefita.presentation.characters.CharactersViewModel.ListState.*
import com.stefita.presentation.common.extensions.observe
import com.stefita.presentation.databinding.CharactersListFragmentBinding
import com.stefita.presentation.entities.CharactersSource
import org.koin.android.viewmodel.ext.android.viewModel


class CharactersListFragment
    : Fragment() {

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
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       (requireActivity() as AppCompatActivity).menuInflater.inflate(R.menu.search_menu_bar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.search)
        val search = menuItem.actionView as SearchView
        searching(search)
        super.onPrepareOptionsMenu(menu)
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { listAdapter.searchInList(it) }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                query.let { listAdapter.searchInList(it) }
                return true
            }
        })
    }

    private fun viewCharacterDetails(character: CharactersSource) {
        val action = CharactersListFragmentDirections.toDetailesViewr(character)
        findNavController().navigate(action)
    }
}