package com.dev.dawaswift.ui.cart.delivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.ui.cart.delivery.ui.createdelivery.CreateDeliveryFragment
import kotlinx.android.synthetic.main.toolback_bar.*

class CreateDelivery : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_delivery_activity)
        ViewUtils().makeFullScreen(this)



        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CreateDeliveryFragment.newInstance())
            .commitNow()


        linear_back.setOnClickListener { onBackPressed() }


    }


}
