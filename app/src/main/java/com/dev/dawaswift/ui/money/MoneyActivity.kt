package com.dev.dawaswift.ui.money

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.product.ViewPagerAdapter
import com.dev.dawaswift.ui.money.ui.money.LoanFragment
import com.dev.dawaswift.ui.money.ui.money.MoneyFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.money_activity.*
import kotlinx.android.synthetic.main.toolback_bar.*

class MoneyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.money_activity)

        ViewUtils().makeFullScreen(this)
        (toolback.findViewById(R.id.txt_back) as TextView).visibility = View.GONE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (toolback).setBackgroundColor(getColor(R.color.colorPrimary))
            txt_title.setTextColor(getColor(R.color.white))

        }
        img_back.setOnClickListener { onBackPressed() }




        setupViewPager(viewpager)
        tabs.tabGravity = TabLayout.GRAVITY_FILL
        tabs.setupWithViewPager(viewpager)
        setupTabIcons()

    }

    private fun setupViewPager(viewPager: ViewPager) {


        val fragment = MoneyFragment()

        val fragment1 = LoanFragment()


        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(fragment, "Money")
        adapter.addFragment(fragment1, "Loan")


        viewPager.offscreenPageLimit = 1
        viewPager.adapter = adapter
    }

    private fun setupTabIcons() {

        tabs.getTabAt(0)?.text = "Money Services"
        tabs.getTabAt(1)?.text = "Loan Services"


    }

}
