package com.dev.dawaswift

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.Constants
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.ui.AuthActivity
import com.dev.common.ui.auth.AuthViewModel
import com.dev.common.utils.viewUtils.ViewUtils

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            if (PrefrenceManager(this).getLoginStatus() == 0) {
                val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                intent.putExtra(Constants().ROLE_CODE, Constants().ROLE_CLIENT)
                startActivityForResult(intent, Constants().SP_SIGN_IN)

            } else {
                startApp()

            }
        }
    }
    val RC_SIGN_IN: Int = 1

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ViewUtils().makeFullScreen(this)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)





        setupUI()

    }

    private fun setupUI() {




    }



    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()


        ViewUtils().makeFullScreen(this)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants().SP_SIGN_IN || requestCode == Constants().GP_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {

                startApp()
            }
        }
//        if (requestCode == RC_SIGN_IN) {
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//
//                var profile: ContactsContract.Profile = ContactsContract.Profile()
//                profile.avatarUri = account?.photoUrl
//                profile.avatar = CommonUtils().uriToString(account?.photoUrl)
//                profile.email = account?.email
//                profile.password = account?.id
//                profile.firstName = account?.givenName
//                profile.lastName = account?.familyName
//
//
//
//                viewModel.saveProfile(Oauth(profile))
//                viewModel.verifyEmail(Oauth(profile))
//            } catch (e: ApiException) {
//                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
//            }
//        }
    }

    fun startApp() {
          val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()

    }


}


