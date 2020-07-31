package com.stefita.presentation.characters

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stefita.presentation.R
import com.stefita.presentation.entities.Status
import kotlinx.android.synthetic.main.activity_characters_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class CharactersListActivity : AppCompatActivity() {

    private val newsList: CharactersViewModel by viewModel()
    private lateinit var listAdapter: CharactersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list)
        listAdapter = CharactersListAdapter()

        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.adapter = listAdapter
        newsList.fetchNews()
    }

    override fun onStart() {
        super.onStart()
        newsList.getNewsLiveData().observe(this, Observer {
            when (it.responseType) {
                Status.ERROR -> {
                    //Error handling
                }
                Status.LOADING -> {
                    //Progress
                }
                Status.SUCCESSFUL -> {
                    // On Successful response
                }
            }
            it.data.let { response ->
                listAdapter.updateList(response)
            }
        })
    }
}