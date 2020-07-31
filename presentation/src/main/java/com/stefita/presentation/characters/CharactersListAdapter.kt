package com.stefita.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stefita.presentation.R
import com.stefita.presentation.entities.CharactersSource
import kotlinx.android.synthetic.main.character_item.view.*


class CharactersListAdapter : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {

    private var articles = mutableListOf<CharactersSource>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(characterItem: CharactersSource) {
            with(itemView) {
                name.text = characterItem.name
            }
        }
    }

    fun updateList(list: List<CharactersSource>? = emptyList()) {
        list?.let {
            if (it.isNotEmpty()) {
                articles.clear()
                articles.addAll(it)
                notifyDataSetChanged()
            }
        }
    }
}