package com.dev.common.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dev.common.R
import com.dev.common.data.Constants
import com.dev.common.data.FRAGMENTS_NAV_KEYS
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.listeners.BackPressHandler
import com.dev.common.listeners.ReplaceFragmentListener
import com.dev.common.models.oauth.Oauth
import com.dev.common.ui.auth.*
import com.dev.common.utils.AccountActionsConstanr
import com.dev.common.utils.viewUtils.ViewUtils

class AuthActivity : AppCompatActivity(), ReplaceFragmentListener {
    override fun <T> replace(currentFragment: FRAGMENTS_NAV_KEYS, nextFragment: FRAGMENTS_NAV_KEYS, data: T?) {
        val oauth = data as Oauth
        var fragment: Fragment? = null
        var fragmentTag: String? = null
        var addToBackStack: Boolean? = true

        when (nextFragment) {
            FRAGMENTS_NAV_KEYS.ONBOARD_PHONE -> {
                fragment = OnBoardPhoneFragment.newInstance()
                fragmentTag = "OnBoardPhoneFragment"
                addToBackStack = true
            }
            FRAGMENTS_NAV_KEYS.ONBOARD_PASSWORD -> {

                fragment = OnBoardPasswordFragment.newInstance()
                fragmentTag = "OnBoardConfirmOtpFragment"
                addToBackStack = true

            }
            FRAGMENTS_NAV_KEYS.ONBOARD_REQUEST_0TP -> {
                if (currentFragment == FRAGMENTS_NAV_KEYS.ONBOARD_PASSWORD) {
                    oauth.profile?.accountAction = AccountActionsConstanr().forgotPasswordAction

                } else {
                    oauth.profile?.accountAction = AccountActionsConstanr().createAccountAction

                }
                fragment = OnBoardRequestOtpFragment.newInstance()
                fragmentTag = "OnBoardRequestOtpFragment"
                addToBackStack = false

            }
            FRAGMENTS_NAV_KEYS.ONBOARD_CONFIRM_OTP -> {
                fragment = OnBoardConfirmOtpFragment.newInstance()
                fragmentTag = "OnBoardConfirmOtpFragment"
                addToBackStack = false

            }
            FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_SP_1 -> {
                fragment = OnBoardRegNamesFragment.newInstance()
                fragmentTag = "OnBoardRegNamesFragment"
                addToBackStack = true

            }


            FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD -> {
                fragment = OnBoardNewPasswordFragment.newInstance()
                fragmentTag = "OnBoardNewPasswordFragment"
                addToBackStack = true

            }
        }
        setOauthObject(oauth)
        if (addToBackStack == true) {
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack(fragmentTag).commit()
            }
        } else {
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commit()
            }
        }

    }

    override fun <T> toActivity(currentFragment: FRAGMENTS_NAV_KEYS, data: T?, finishPrevious: Boolean) {
        when (currentFragment) {
            FRAGMENTS_NAV_KEYS.ONBOARD_PASSWORD -> {
                setOauthObject(data as Oauth)

            }

            FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD -> {
                setOauthObject(data as Oauth)

            }
        }
        prefrenceManager.setLoginStatus(1)


        startApp()
    }


    private var registeredHandler: BackPressHandler? = null
    //  fun registerHandler(handler: BackPressHandler) { registeredHandler = handler }
    // fun unregisterHandler(handler: BackPressHandler) { registeredHandler = null }
    private lateinit var viewModel: AuthViewModel

    private lateinit var prefrenceManager: PrefrenceManager


    var role: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        prefrenceManager = PrefrenceManager(this)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        role = intent.getIntExtra(Constants().ROLE_CODE, 0)


        if (savedInstanceState == null) {

             supportFragmentManager.beginTransaction()
                    .replace(R.id.container, OnBoardPhoneFragment.newInstance())
                    .commitNow()

        }
    }

    override fun onResume() {
        super.onResume()
        ViewUtils().makeFullScreen(this)

    }
//
//    override fun onBackPressed() {
//       // if (registeredHandler?.onBackPressed() != false) {
//            super.onBackPressed()
//       // }
//
//    }

    fun getOauthObject(): Oauth {
        return viewModel.getProfile()
    }

    fun setOauthObject(oauth: Oauth) {
        oauth.profile?.roleId = role
        prefrenceManager.saveProfile(oauth)
        viewModel.saveProfile(oauth)


    }

    fun startApp() {
//        if (viewModel.getProfile().profile?.roleId == Constants().ROLE_VISITOR) {
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        if (viewModel.getProfile().profile?.roleId == Constants().ROLE_HOST) {
//            val intent = Intent(applicationContext, HostActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        val data = Intent()
        data.putExtra("data", getOauthObject())
        setResult(RESULT_OK, data)
        finish()
    }

}



