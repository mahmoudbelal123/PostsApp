package com.mahmoud.belal.postsapp.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mahmoud.belal.postsapp.R
import com.mahmoud.belal.postsapp.core.observe
import com.mahmoud.belal.postsapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var toastHelper: ToastHelper
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // It's very important to set the toolbar to prevent errors of the NPE type,
        // this is because the application style is .NoActionBar
        //setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        toastHelper.toastMessages.observe(this) {
            showToast(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}