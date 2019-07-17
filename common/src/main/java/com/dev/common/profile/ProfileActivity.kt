package com.dev.common.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.R
import com.dev.common.profile.ui.profile.ProfileFragment
import com.dev.common.utils.viewUtils.ViewUtils

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        ViewUtils().makeFullScreen(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProfileFragment.newInstance())
                .commitNow()
        }
    }

}
