package com.example.shopapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.shopapp.interfaces.ApiService
import com.example.shopapp.common.Common
import com.example.shopapp.interfaces.RegisterRequest
import com.example.shopapp.interfaces.RegisterResponse
import com.example.shopapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var apiService: ApiService
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userLogin = findViewById<EditText>(R.id.userLogin)
        val userEmail = findViewById<EditText>(R.id.userEmail)
        val userPassword = findViewById<EditText>(R.id.userPassword)
        val button = findViewById<Button>(R.id.buttonReg)

        val retrofit = RetrofitClient.getClient("https://188.168.215.162/")
        apiService = retrofit.create(ApiService::class.java)

        dialog = AlertDialog.Builder(this)
            .setTitle("Подождите")
            .setMessage("Регистрация...")
            .setCancelable(false)
            .create()

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Инициализация (если ещё не сделана)
            if (!::apiService.isInitialized) {
                val retrofit = RetrofitClient.getClient("https://ваш-домен.ru/api/")
                apiService = retrofit.create(ApiService::class.java)
            }

            if (!::dialog.isInitialized) {
                dialog = AlertDialog.Builder(this)
                    .setMessage("Регистрация...")
                    .setCancelable(false)
                    .create()
            }

            lifecycleScope.launch {
                dialog.show()
                try {
                    val request = RegisterRequest(login, password, email)
                    val response = apiService.register(request)

                    dialog.dismiss()

                    if (response.isSuccessful) {
                        val user = response.body()
                        Toast.makeText(this@MainActivity, "Регистрация успешна!\nПривет, ${user?.Nickname}!", Toast.LENGTH_LONG).show()
                        // TODO: переход на другую активность
                    } else {
                        val error = when (response.code()) {
                            400 -> "Некорректные данные"
                            409 -> "Пользователь с таким именем/почтой уже существует"
                            else -> "Ошибка сервера (${response.code()})"
                        }
                        Toast.makeText(this@MainActivity, "❌ $error", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    dialog.dismiss()
                    Toast.makeText(this@MainActivity, "🚫 Ошибка сети: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun registerUser(login: String, email: String, password: String) {
        // Создайте диалог ОДИН РАЗ — например, в onCreate или отдельно
        if (!::dialog.isInitialized) {
            dialog = AlertDialog.Builder(this)
                .setTitle("Регистрация")
                .setMessage("Отправка данных...")
                .setCancelable(false)
                .create()
        }

        dialog.show()

        lifecycleScope.launch {
            try {
                val response = apiService.register(RegisterRequest(login, password, email))

                dialog.dismiss()

                if (response.isSuccessful) {
                    val body = response.body() ?: ""
                    Toast.makeText(this@MainActivity, "Успех: $body", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Ошибка сервера: ${response.code()} — ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                dialog.dismiss()
                Toast.makeText(this@MainActivity, "Ошибка сети: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }
}