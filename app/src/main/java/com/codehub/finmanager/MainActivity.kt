package com.codehub.finmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.codehub.finmanager.databinding.ActivityMainBinding
import com.codehub.finmanager.ui.FinManagerViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Runnable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val finManagerViewModel: FinManagerViewModel by viewModels()
    private var backPressedTime=0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        finManagerViewModel.auth = FirebaseAuth.getInstance()
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
            when(destination.id){
                R.id.dashboard, R.id.statistics, R.id.createProfileFragment->{
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user==null){
                        navController.navigate(R.id.dashboard)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (backPressedTime+3000>System.currentTimeMillis()){
            super.onBackPressed()
            finish()
            FirebaseAuth.getInstance().signOut()
        }else{
        Toast.makeText(this, "Tap back again to exit the app", Toast.LENGTH_LONG).show()
        }
        backPressedTime= System.currentTimeMillis()

    }
    fun handleBottomBarVisibility(beVisible:Boolean) {
        if (beVisible){
            binding.apply{
                fabAddIncomeExpense.visibility = View.VISIBLE
                bottomNav.visibility = View.VISIBLE
                bottomNav.fabCradleRoundedCornerRadius = 16f
            }
        }else{
            binding.apply {
                fabAddIncomeExpense.visibility = View.GONE
                bottomNav.visibility = View.GONE
                bottomNav.fabCradleRoundedCornerRadius = 16f

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