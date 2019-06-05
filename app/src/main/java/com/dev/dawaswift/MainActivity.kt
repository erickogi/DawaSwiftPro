package com.dev.dawaswift

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dev.dawaswift.ui.OrdersFragment
import com.dev.cabinzz.ui.main.MenuFragment
import com.dev.cabinzz.ui.main.ProfileFragment
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.ui.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val menuFragment = MenuFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewUtils().makeFullScreen(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
        setHomeSelectedNav()
        val badge = navigation.showBadge(R.id.navigation_profile)
        badge.number = 2
    }

    private fun popOutFragments() {
        val fragmentManager = supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }
    fun setCatSelectedNav() {

        if (navigation.selectedItemId != R.id.navigation_categories) {
            navigation.selectedItemId = R.id.navigation_categories
        }
    }

    fun setProfileSelectedNav() {
        if (navigation.selectedItemId != R.id.navigation_profile) {
            navigation.selectedItemId = R.id.navigation_profile
        }
    }

    fun setHomeSelectedNav() {

        if (navigation.selectedItemId != R.id.navigation_home) {
            navigation.selectedItemId = R.id.navigation_home
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                popOutFragments()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                popOutFragments()

                supportFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment.newInstance())
                    .addToBackStack("profile").commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_categories -> {
                popOutFragments()

                supportFragmentManager.beginTransaction().replace(R.id.container, OrdersFragment.newInstance())
                    .addToBackStack("Fav").commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.container)
        if (f is MainFragment) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Are You Sure?")


            builder.setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }

            builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()            //additional code
        } else {
            popOutFragments()
            navigation.selectedItemId = R.id.navigation_home
        }
    }

    fun dismissSheet() {
        menuFragment.dismiss()
    }

    fun showSheet() {
        menuFragment.show(supportFragmentManager, menuFragment.tag)

    }

}
