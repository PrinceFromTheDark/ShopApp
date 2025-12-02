package com.example.shopapp

import android.graphics.Bitmap
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopapp.databinding.FragmentCatalogueBinding
import com.example.shopapp.databinding.FragmentItemBinding
import com.example.shopapp.dto.GameDTO
import kotlinx.serialization.json.Json

class ItemFragment : Fragment() {

    private lateinit var binding: FragmentItemBinding

    private lateinit var game: GameDTO
    companion object {
        fun newInstance() = ItemFragment()
    }

    private val viewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            bundle.getString("Game")?.let { gameStr ->
                game = Json.decodeFromString(gameStr)
            }
        }
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = game.title

        val res = loadBase64Image(game.logo)
        if (res != null) binding.image.setImageBitmap(res as Bitmap)
        else binding.image.setImageResource(R.drawable.ic_launcher_foreground)

        binding.price.text = String.format("%.2f", game.price)
        binding.description.text = game.description

    }
}