package com.codehub.finmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.codehub.finmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val naHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = naHostFragment.navController
        binding.apply {
            bottomNavView.setupWithNavController(navController)
            fabAddIncomeExpense.setOnClickListener {
                navController.navigate(R.id.addTransaction)
            }
        }

        navController.addOnDestinationChangedListener{_, destination, _ ->
          /*  when(destination.id){
                // not login/signup screens
                // going to dashboard if user is authenticated
                R.id.login, R.id.signUp->{
                    // user null --->navigate to login
                    binding.bottomNav.visibility = View.GONE
                    binding.fabAddIncomeExpense.visibility = View.GONE
                }
                R.id.dashboard ->{
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.fabAddIncomeExpense.visibility = View.VISIBLE
                }
            }*/
        }
    }
    fun handleBottomBarVisibility(beVisible:Boolean) {
        if (beVisible){
            binding.apply{
                bottomNav.visibility = View.VISIBLE
                fabAddIncomeExpense.visibility = View.VISIBLE
            }
        }else{
            binding.apply {
                bottomNav.visibility = View.GONE
                fabAddIncomeExpense.visibility = View.GONE
            }
        }
    }

    fun handleBottomBarActions(){
        navController.addOnDestinationChangedListener{ _, destination,_ ->
            /*binding.bottomNav.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId){
                    R.id.dashboard  ->{
                        navController.navigate(R.id.dashboard)
                   true
                    }
                    R.id.profile ->{
                        Toast.makeText(this, "To Account Screen", Toast.LENGTH_SHORT).show()
                   true
                    }
                    R.id.statistics -> {
                        navController.navigate(R.id.statistics)
                        true
                    }
                    else -> {false}
                }

            }*/
        }
    }
}