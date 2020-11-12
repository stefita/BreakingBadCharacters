package com.stefita.presentation.characters

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
    private var availableSeasons: MutableList<DropDownItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharactersListFragmentBinding.inflate(layoutInflater)
        with(binding.recyclerView) {
            layoutManager = GridLayoutManager(context, 3)
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
                    if (viewModel.savedQuery.isNotBlank() || viewModel.savedSeason > 0) {
                        searchView.setQuery(viewModel.savedQuery, false)
                        spinner.setSelection(viewModel.savedSeason)
                        listAdapter.filter(viewModel.savedQuery, viewModel.savedSeason)
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

    private fun setupSpinner(list: MutableList<DropDownItem> = mutableListOf()) {
        spinnerAdapter = ArrayAdapter( requireContext(), android.R.layout.simple_spinner_item, list)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter.setNotifyOnChange(true)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                adapterView: AdapterView<*>, view: View, season: Int, l: Long
            ) {
                viewModel.savedSeason = availableSeasons[season].index
                listAdapter.filter(searchView.query.toString(), viewModel.savedSeason)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }
    }

    private fun updateSpinnerEpisodes(list: List<Int>) {
        availableSeasons.clear()
        availableSeasons.add(DropDownItem(0, getString(R.string.seasons)))
        availableSeasons.addAll(list.map { DropDownItem(it, "Season $it") })
    }

    private fun setSearchListeners(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { listAdapter.filter(it, (spinner.selectedItem as DropDownItem).index) }
                viewModel.savedQuery = query ?: ""
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                query.let { listAdapter.filter(it, (spinner.selectedItem as DropDownItem).index) }
                viewModel.savedQuery = query
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