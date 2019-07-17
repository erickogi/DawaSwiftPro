package com.dev.dawaswiftdriver.views.travel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.views.travel.ui.trips.TripsFragment
import kotlinx.android.synthetic.main.toolback_bar.*

class Trips : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trips_activity)
        ViewUtils().makeFullScreen(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TripsFragment.newInstance())
                .commitNow()
        }
        linear_back.setOnClickListener {
            onBackPressed()
        }
    }

}
