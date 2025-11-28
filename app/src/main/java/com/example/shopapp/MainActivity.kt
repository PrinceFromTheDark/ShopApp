package com.example.shopapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.shopapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        binding.navView.setOnItemSelectedListener({ item ->
            if (item.itemId == R.id.catalogue) {
                navController.navigate(R.id.navCatalogueFragment)
            }
            if (item.itemId == R.id.history) {
                navController.navigate(R.id.navCatalogueFragment)
            }
            return@setOnItemSelectedListener true
        } )

        navController = findNavController(R.id.fragmentContainerView)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller: NavController,
                                                        destination: NavDestination,
                                                        arguments: Bundle? ->
            if (supportFragmentManager.backStackEntryCount < 1) {
//                supportActionBar?.setDisplayShowHomeEnabled(false)
//                supportActionBar?.setHomeButtonEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        navController.navigate(R.id.navLoginFragment)


    }



    override fun onSupportNavigateUp(): Boolean {
        Log.d("BACKSTACK", supportFragmentManager.backStackEntryCount.toString())
//        return false
        val r = super.onSupportNavigateUp() && supportFragmentManager.backStackEntryCount > 3
//        return false
        return r
    }

    fun showNavBar() {
        binding.navView.visibility = BottomNavigationView.VISIBLE
    }


}