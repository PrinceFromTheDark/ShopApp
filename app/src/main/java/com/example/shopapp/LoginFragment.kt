package com.example.shopapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.window.application
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.shopapp.databinding.FragmentLoginBinding
import com.example.shopapp.interfaces.ApiService
import com.example.shopapp.interfaces.SignInRequest
import com.example.shopapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var apiService: ApiService

    private lateinit var sessionManager: SessionManager

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()

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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignUp.setOnClickListener {
//            val extra = Bundle()
//            extra.putString(ARG_COLLECTION_ITEM, json().encodeToString(item))
            findNavController().navigate(R.id.navRegisterFragment)
        }

        binding.buttonSignIn.setOnClickListener {
            val login = binding.userLogin.text.toString().trim()
            val password = binding.userPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dialog = AlertDialog.Builder(requireActivity())
                .setTitle("Подождите")
                .setMessage("Вход в аккаунт...")
                .setCancelable(false)
                .create()

            lifecycleScope.launch {
                dialog.show()
                try {
                    val request = SignInRequest(login, password)
                    val response = apiService.login(request)

                    dialog.dismiss()

                    if (response.isSuccessful) {
                        Toast.makeText(requireActivity(), "Вход в аккаунт успешен!\nПривет!", Toast.LENGTH_LONG).show()
                        sessionManager.authToken = response.body()!!.token

//                        val cl = DCL()
//                        findNavController().addOnDestinationChangedListener(cl)
                        sessionManager.userId = apiService.getUser().body()!!.id
                        findNavController().navigate(R.id.navCatalogueFragment)
//                        findNavController().removeOnDestinationChangedListener(cl)
                        (activity as MainActivity).showNavBar()
                    } else {
                        val error = when (response.code()) {
                            400 -> "Некорректные данные"
                            409 -> "Пользователь с таким именем/почтой уже существует"
                            else -> "Ошибка сервера (${response.code()})"
                        }
                        Toast.makeText(requireActivity(), "❌ $error", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    dialog.dismiss()
                    Toast.makeText(requireActivity(), "🚫 Ошибка сети: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.d("error", e.message!!);
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (sessionManager.authToken != null) {
            lifecycleScope.launch {
                if (apiService.validateToken().isSuccessful) {
                    findNavController().navigate(R.id.navCatalogueFragment)
                    (activity as MainActivity).showNavBar()
                }
            }
        }
    }

    internal class DCL: NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
           controller.graph.setStartDestination(R.id.navCatalogueFragment)

            controller.clearBackStack(R.id.navLoginFragment)
        }

    }
}