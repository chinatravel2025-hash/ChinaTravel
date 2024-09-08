package com.china.travel

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.router.ARouterPathList
import com.china.travel.databinding.ActivityMainBinding
import com.example.base.base.User
import com.example.base.utils.LogUtils
import com.example.base.utils.StatusBarUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.travel.guide.common.LoginRepository


@Route(path = ARouterPathList.APP_MAIN)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var toolbar: Toolbar? = null
    private var statusBarDarkTheme: Boolean = true
        set(value) {
            field = value
            StatusBarUtil.setStatusBarDarkTheme(this, value)
        }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        StatusBarUtil.immersive(this)
        statusBarDarkTheme = true
        setUpBottomNavigationBar()
        binding.navView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
        }
        User.currentUser.observe(this){
            LoginRepository.repository.imLogin()
        }
    }

    private fun setUpBottomNavigationBar() {
        val bottomNavigationView = binding.navView
        val colors = intArrayOf(
            this@MainActivity.getColor(com.example.peanutmusic.base.R.color.txt_FF666666),
            this@MainActivity.getColor(com.example.peanutmusic.base.R.color.txt_00B386)
        )
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
        )
        bottomNavigationView.itemTextColor = ColorStateList(states, colors)
        bottomNavigationView.itemIconTintList = null
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        // Setup the ActionBar with navController and top level destinations
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_guide, R.id.navigation_profile))
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.arch_toolbar)
        if (toolbar != null) {
            toolbar?.title = ""
            setSupportActionBar(toolbar)
            toolbar?.apply {
                navigationContentDescription = null
                setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
            supportActionBar?.hide()
        }
    }


}