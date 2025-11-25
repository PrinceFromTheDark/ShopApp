package com.example.shopapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopapp.databinding.FragmentCatalogueBinding
import com.example.shopapp.databinding.FragmentLoginBinding

class CatalogueFragment : Fragment() {

    private lateinit var binding: FragmentCatalogueBinding

    companion object {
        fun newInstance() = CatalogueFragment()
    }

    private val viewModel: CatalogueViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogueBinding.inflate(inflater, container, false)
        return binding.root
    }
}