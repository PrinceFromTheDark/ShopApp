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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shopapp.databinding.FragmentRegisterBinding
import com.example.shopapp.interfaces.ApiService
import com.example.shopapp.interfaces.SignUpRequest
import com.example.shopapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    var apiService = RetrofitClient.getClient("http://10.0.2.2:5027/").create(ApiService::class.java)

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = AlertDialog.Builder(requireActivity())
            .setTitle("Подождите")
            .setMessage("Регистрация...")
            .setCancelable(false)
            .create()

        binding.buttonReg.setOnClickListener {
            val login = binding.userLogin.text.toString().trim()
            val email = binding.userEmail.text.toString().trim()
            val password = binding.userPassword.text.toString().trim()

            if (login.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                dialog.show()
                try {
                    val request = SignUpRequest(login, password, email)
                    val response = apiService.register(request)

                    dialog.dismiss()

                    if (response.isSuccessful) {
                        val user = response.body()
                        Toast.makeText(requireActivity(), "Регистрация успешна!\nПривет!", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.navCatalogueFragment)
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
}