package com.dev.dawaswift.ui.pharmacies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.data.Constants
import com.dev.dawaswift.R
import com.dev.dawaswift.ui.pharmacies.ui.pharmacy.PharmacyFragment

class Pharmacy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pharmacy_activity)
        if (savedInstanceState == null) {
            var fragment = PharmacyFragment()


            var action = Constants().PHARMACY
            try {
                if (intent.getStringExtra(Constants().PHARMACYACTION) != null) {
                    action = intent.getStringExtra(Constants().PHARMACYACTION)
                }
            } catch (x: Exception) {
                x.printStackTrace()
            }


            fragment
                .arguments = Bundle().apply {
                putString(Constants().PHARMACYACTION, action)
            }
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()


        }
    }

}
