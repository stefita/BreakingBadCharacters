package com.stefita.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stefita.presentation.R
import com.stefita.presentation.common.extensions.loadUrl
import com.stefita.presentation.entities.CharactersSource
import kotlinx.android.synthetic.main.character_item.view.*


class CharactersListAdapter(
    private val onView: (CharactersSource) -> Unit
) : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    private var characters = mutableListOf<CharactersSource>()
    private var displayedCharacters = mutableListOf<CharactersSource>()

    override fun getItemId(position: Int): Long = displayedCharacters[position].id.toLong()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return displayedCharacters.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(displayedCharacters[position], onView)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(characterItem: CharactersSource, onView: (CharactersSource) -> Unit) {
            with(itemView) {
                name.text = characterItem.name
                card.setOnClickListener { onView(characterItem) }
                picture.loadUrl(characterItem.img)
            }
        }
    }

    fun updateList(list: List<CharactersSource>) {
        if (list.isNotEmpty()) {
            characters.clear()
            characters.addAll(list)
            displayedCharacters.clear()
            displayedCharacters.addAll(characters)
            notifyDataSetChanged()
        }
    }

    fun filter(query: String = "", season: Int = 0) {
        val seasonSel = if (season > 0) season else null
        displayedCharacters = characters.filter { list ->
            list.name.contains(query, ignoreCase = true) &&
                    seasonSel?.let { list.appearance?.contains(it) } ?: true
        }.toMutableList()
        notifyDataSetChanged()
    }
}