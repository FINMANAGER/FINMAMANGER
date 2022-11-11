package com.codehub.finmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.codehub.finmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    // private lateinit var appBarConfiguration:AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val naHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = naHostFragment.navController
        // appBarConfiguration = AppBarConfiguration()

        //handle conditional navigation(authentication)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                // not login/signup screens
                // going to dashboard if user is authenticated
                1->{
                    // user null --->navigate to login
                }
            }


        }
    }
}