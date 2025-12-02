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

class CatalogueFragment : Fragment() {

    private lateinit var binding: FragmentCatalogueBinding

    private var items = ArrayList<GameDTO>()
    private var page = 1
    private var itemsLoading = false

    private lateinit var apiService: ApiService

    private lateinit var sessionManager: SessionManager

    companion object {
        fun newInstance() = CatalogueFragment()
    }

    private val viewModel: CatalogueViewModel by viewModels()

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

        getGames(startIndex = 1 + GlobalVars.pageSize * page, itemCount = GlobalVars.pageSize, page = page) { gameList, startIndex, itemCount ->
            items.addAll(gameList)
            binding.itemsList.adapter?.notifyItemRangeInserted(startIndex, itemCount)
            page++
        }

        binding.itemsList.layoutManager = LinearLayoutManager(context)
        binding.itemsList.adapter = ItemListAdapter(sessionManager, items)
        val itemListAdapter: ItemListAdapter = binding.itemsList.adapter as ItemListAdapter

        itemListAdapter.onItemClick = { game ->
            val arguments = Bundle()
            arguments.putString("Game", Json.encodeToString(GameDTO.serializer(), game))
            findNavController().navigate(R.id.navItemFragment, arguments)
        }

        binding.itemsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager // Or your specific LayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    getGames(startIndex = 1 + GlobalVars.pageSize * page, itemCount = GlobalVars.pageSize, page = page) { gameList, startIndex, itemCount ->
                        items.addAll(gameList)
                        binding.itemsList.adapter?.notifyItemRangeInserted(startIndex, itemCount)
                        page++
                    }
                }
            }
        })
    }

    fun getGames(startIndex: Int, itemCount: Int, page: Int, onGamesLoaded: (gameList: List<GameDTO>, startIndex: Int, itemCount: Int) -> Unit = {gameList, startIndex, itemCount -> }) {
        lifecycleScope.launch {
            if (!itemsLoading) {
                itemsLoading = true
                try {
                    val response = apiService.getGames(page = page)

                    if (response.isSuccessful) {
                        response.body()?.let { games ->
                            onGamesLoaded(games, startIndex, itemCount)
                        }

//                        val cl = DCL()
//                        findNavController().addOnDestinationChangedListener(cl)

//                        findNavController().removeOnDestinationChangedListener(cl)
                    } else {
                        val error = when (response.code()) {
                            400 -> "Некорректные данные"
                            409 -> "Пользователь с таким именем/почтой уже существует"
                            else -> "Ошибка сервера (${response.code()})"
                        }
                        Toast.makeText(requireActivity(), "❌ $error", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        "🚫 Ошибка сети: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("error", e.message!!);
                    e.printStackTrace()
                }
                itemsLoading = false
            }
        }
    }
}