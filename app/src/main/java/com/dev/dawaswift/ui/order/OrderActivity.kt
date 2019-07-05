package com.dev.dawaswift.ui.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Order.OrderItems
import com.dev.dawaswift.ui.order.ui.order.OrderFragment

class OrderActivity : AppCompatActivity() {
    var selectedorder: OrderItems? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_activity)
        ViewUtils().makeFullScreen(this)

        selectedorder = intent.getSerializableExtra("data") as OrderItems
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, OrderFragment.newInstance())
                .commitNow()
        }
    }

}
