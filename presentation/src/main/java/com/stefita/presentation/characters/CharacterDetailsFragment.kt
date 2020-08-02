package com.stefita.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.stefita.presentation.databinding.CharacterDetailsFragmentBinding
import com.stefita.presentation.databinding.CharactersListFragmentBinding

class CharacterDetailsFragment : Fragment() {

    val args by navArgs<CharacterDetailsFragmentArgs>()
    private lateinit var binding: CharacterDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailsFragmentBinding.inflate(layoutInflater)
        binding.character = args.character
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}