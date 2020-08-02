package com.stefita.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stefita.presentation.R
import com.stefita.presentation.entities.CharactersSource
import kotlinx.android.synthetic.main.character_item.view.*


class CharactersListAdapter(
    val onView: (CharactersSource) -> Unit
) : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    private var characters = mutableListOf<CharactersSource>()

    override fun getItemId(position: Int): Long = characters[position].id.toLong()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position], onView)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(characterItem: CharactersSource, onView: (CharactersSource) -> Unit) {
            with(itemView) {
                name.text = characterItem.name
                name.setOnClickListener { onView(characterItem) }
            }
        }
    }

    fun updateList(list: List<CharactersSource>) {

            if (list.isNotEmpty()) {
                characters.clear()
                characters.addAll(list)
                notifyDataSetChanged()
            }
    }
}