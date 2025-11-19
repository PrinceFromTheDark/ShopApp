package com.example.shopapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
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

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()
            if(login == "" || email == "" || password == "") {
                Toast.makeText(this, "Не все поля были заполнены!", Toast.LENGTH_SHORT).show()
            }
            else {
                val user = User(login, email, password)
            }
        }
    }
}