package com.stefita.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var searchView: SearchView
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<DropDownItem>
    private var availableSeasons: MutableList<Int> = mutableListOf()

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
                    updateSpinnerEpisodes(charState.availableSeasons)
                    if (viewModel.savedSearch.isNotBlank()) {
                        searchView.setQuery(viewModel.savedSearch, false)
                        listAdapter.searchInList(viewModel.savedSearch)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       (requireActivity() as AppCompatActivity).menuInflater.inflate(R.menu.search_menu_bar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.search)
        searchView = menuItem.actionView as SearchView
        setSearchListeners(searchView)
        spinner = menu.findItem(R.id.season).actionView as Spinner
        setupSpinner(availableSeasons)
        super.onPrepareOptionsMenu(menu)
    }

    private fun setupSpinner(list: MutableList<Int> = mutableListOf()) {
        val seasonsPairs = list.map { DropDownItem(it, "Season $it") }
        spinnerAdapter = ArrayAdapter( requireContext(), android.R.layout.simple_spinner_item, seasonsPairs)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter.setNotifyOnChange(true)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, season: Int, l: Long) {
                listAdapter.filterForSeason(season)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }
    }

    private fun updateSpinnerEpisodes(list: List<Int>) {
        spinnerAdapter.clear()
        spinnerAdapter.add(DropDownItem(0, getString(R.string.seasons)))
        spinnerAdapter.addAll(list.map { DropDownItem(it, "Season $it") })
        spinnerAdapter.notifyDataSetChanged()
    }

    private fun setSearchListeners(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { listAdapter.searchInList(it) }
                viewModel.savedSearch = query ?: ""
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                query.let { listAdapter.searchInList(it) }
                viewModel.savedSearch = query
                return true
            }
        })
    }

    private fun viewCharacterDetails(character: CharactersSource) {
        val action = CharactersListFragmentDirections.toDetailesViewr(character)
        findNavController().navigate(action)
    }
}

data class DropDownItem(val index: Int, val name: String) {

    override fun toString(): String {
        return name
    }
}