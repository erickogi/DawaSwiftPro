package com.dev.dawaswift.ui.prescription

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.data.Constants
import com.dev.dawaswift.R
import com.dev.dawaswift.models.pharmacy.Pharmacy
import com.dev.dawaswift.ui.prescription.ui.prescription.PrescriptionFragment

class PrescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prescription_activity)
        if (savedInstanceState == null) {

            var fragment = PrescriptionFragment()

            var pahrmacy: Pharmacy? = null
            try {
                pahrmacy = intent.getSerializableExtra(Constants().PHARMACY) as Pharmacy
            } catch (x: Exception) {
                x.printStackTrace()
            }


            fragment
                .arguments = Bundle().apply {
                putSerializable(Constants().PHARMACY, pahrmacy)
            }
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()


        }
    }

}
