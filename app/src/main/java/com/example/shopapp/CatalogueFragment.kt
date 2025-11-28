package com.example.shopapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = ArrayList<Item>()

        items.add(Item("Типо игра", "Картинка", 10f))

        binding.itemsList.layoutManager = LinearLayoutManager(context)
        binding.itemsList.adapter = ItemListAdapter(items)
    }
}