package com.example.coffeehopper.presentationlayer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coffeehopper.R
import com.example.coffeehopper.databinding.ActivityCoffeeHopperBinding
import com.example.coffeehopper.presentationlayer.viewmodels.AuthenticationViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeeHopperActivity : AppCompatActivity() {

    private val viewModel by viewModel<AuthenticationViewModel>()
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding:ActivityCoffeeHopperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeHopperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up bottom nav view navigation
        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController: NavController = navHostFragment!!.findNavController()

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.coffeeListFragment, R.id.coffeeMapFragment, R.id.coffeeFavoritesFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        observeAuthenticationState()

        loginLauncher = getLauncher()
    }

    private fun getLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // pop the blank off stack
            } else {
                loginFlow()
            }
        }
    }

    private fun loginFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        loginLauncher.launch(
            AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.Theme_CoffeeHopper)
                .setAvailableProviders(
                    providers
                ).setIsSmartLockEnabled(false).build()
        )
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(this) { authenticationState ->
            when(authenticationState) {
                AuthenticationViewModel.AuthenticationState.AUTHENTICATED -> {
                    // pop blank off stack
                }
                else -> {
                    loginFlow()
                }
            }
        }
    }

}