package com.dev.dawaswiftdriver.views.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.views.account.ui.myprofile.ProfileFragment

class MyProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_activity)
        ViewUtils().makeFullScreen(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProfileFragment.newInstance())
                .commitNow()
        }
    }

}
