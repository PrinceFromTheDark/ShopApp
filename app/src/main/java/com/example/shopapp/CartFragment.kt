package com.example.shopapp

import android.content.ClipData.Item
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.shopapp.databinding.FragmentCatalogueBinding
import com.example.shopapp.databinding.FragmentLoginBinding
import com.example.shopapp.dto.GameDTO
import com.example.shopapp.interfaces.ApiService
import com.example.shopapp.interfaces.SignInRequest
import com.example.shopapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.math.BigDecimal

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCatalogueBinding

    private var items = ArrayList<GameDTO>()
    private var page = 1
    private var itemsLoading = false

    private lateinit var apiService: ApiService

    private lateinit var sessionManager: SessionManager

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiService = RetrofitClient.getClient("http://10.0.2.2:5027/", requireContext()).create(ApiService::class.java)

        val myApplication = requireActivity() as MainActivity
        sessionManager = myApplication.sessionManager
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

        // TODO: заменить получение данных с сервера на данные из shared prefs
        items.addAll(sessionManager.itemsInCart)


//        binding.itemsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager // Or your specific LayoutManager
//                val visibleItemCount = layoutManager.childCount
//                val totalItemCount = layoutManager.itemCount
//                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
//
//                // TODO: заменить получение данных с сервера на данные из shared prefs и убрать пагинацию, думаю лишнее
//
//            }
//        })
    }
}