package com.dev.dawaswift.ui.productsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.dawaswift.R
import com.dev.dawaswift.ui.productsearch.ui.ProductSearchFragment

class ProductSearch : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_search_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    ProductSearchFragment.newInstance()
                )
                .commitNow()
        }


    }

}
