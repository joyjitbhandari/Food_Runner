package com.joyjit.foodrunner.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.FrameLayout
import com.joyjit.foodrunner.R
import com.joyjit.foodrunner.fragment.FAQFragment
import com.joyjit.foodrunner.fragment.FavouritesFragment
import com.joyjit.foodrunner.fragment.HomeFragment
import com.joyjit.foodrunner.fragment.ProfileFragment

lateinit var sharedPreferences: SharedPreferences
lateinit var drawerLayout: DrawerLayout
lateinit var coordinatorLayout: CoordinatorLayout
lateinit var toolbar: androidx.appcompat.widget.Toolbar
lateinit var frameLayout: FrameLayout
lateinit var navigationView: NavigationView

class Home_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home__page)
        var previousMenuItem: MenuItem? = null

        var titlename = intent.getStringExtra("title")
        if (titlename != null) {
            title = "Hii  $titlename"
        }
        sharedPreferences = getSharedPreferences(getString(R.string.Preference_file), MODE_PRIVATE)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)

        setUpToolbar()

        openhomepage()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@Home_Page,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked = false
            }
            it.isCheckable
            it.isChecked
            previousMenuItem = it
            when(it.itemId){
                R.id.homePage -> {
                    openhomepage()
                    drawerLayout.closeDrawers()
                }
                R.id.favouritesPage ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FavouritesFragment())
                        .commit()
                    supportActionBar?.title = "Favourites Page"
                    drawerLayout.closeDrawers()
                }
                R.id.profilePage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "Profile Page"
                    drawerLayout.closeDrawers()
                }
                R.id.faqPage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FAQFragment())
                        .commit()
                    supportActionBar?.title = "FAQ Page"
                    drawerLayout.closeDrawers()

                }
                R.id.logOutPage -> {
                    sharedPreferences.edit().clear().apply()
                    setContentView(R.layout.activity_login)
                    finish()
                }
            }
            return@setNavigationItemSelectedListener(true)
        }
    }

    fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Food Runner"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun openhomepage(){
        val fragment = HomeFragment()
        val transaction =supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, HomeFragment())
        transaction.commit()
        supportActionBar?.title = "Home Page"
        navigationView.setCheckedItem(R.id.homePage)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)
        when(frag){
            !is HomeFragment -> openhomepage()
            else ->super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id =item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

}