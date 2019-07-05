package com.dev.dawaswift.ui.category

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.data.Constants
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.ui.category.ui.categories.CategoriesFragment
import com.dev.dawaswift.ui.category.ui.categories.HealthAreasFragment
import com.dev.dawaswift.ui.productsearch.ProductSearch
import kotlinx.android.synthetic.main.toolback_bar.*

class Categories : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories_activity)
        ViewUtils().makeFullScreen(this)
        (toolback.findViewById(R.id.txt_back) as TextView).visibility= View.GONE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (toolback).setBackgroundColor(getColor(R.color.colorPrimary))
            txt_title.setTextColor(getColor(R.color.white))

        }
        img_back.setOnClickListener{onBackPressed()}



        if (savedInstanceState == null) {
            if(intent.getIntExtra(Constants().RESOURCE,0)==Constants().CATEGORIES) {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CategoriesFragment.newInstance())
                    .commitNow()
                txt_title.text = "Categories"
            }else{

                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HealthAreasFragment.newInstance())
                    .commitNow()
                txt_title.text = "Health Areas"


            }
        }
    }


    fun onselection() {
        if (intent.getStringExtra(Constants().CATEGORYACTION) == Constants().CALLPRODUCTSEARCH) {
            startActivity(Intent(this, ProductSearch::class.java))
            finish()
        } else {
            finish()
        }
    }



}
