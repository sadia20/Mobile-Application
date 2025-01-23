package com.sadia20.mobileapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.sadia20.CredentialsManager
import com.sadia20.FragmentA
import com.sadia20.FragmentB
import com.sadia20.RecipesFragment
import com.sadia20.SignIn
import com.sadia20.mobileapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    var credentialsManager = CredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
//        val navController = navHostFragment.navController

        // Set up the initial fragment(Fragments sample + PR)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, FragmentA())
//                .commit()
//        }

        // Set up the initial fragment(Switch Login and Register activities into fragments + PR)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, SignIn())
//                .commit()
//        }

        // Set up the button click listener(Recipe (or other) RecyclerView + PR)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RecipesFragment())
                .commit()
        }

//        // Set up the button click listener(Fragments sample + PR)
//        binding?.btnToggleFragment?.setOnClickListener {
//            toggleFragment()
//        }
    }

    // Toggle between FragmentA and FragmentB when the button is clicked(Fragments sample + PR)
    private fun toggleFragment() {
        val fragment =
            if (supportFragmentManager.findFragmentById(R.id.fragmentContainer) is FragmentA) {
                FragmentB()
            } else {
                FragmentA()
            }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}