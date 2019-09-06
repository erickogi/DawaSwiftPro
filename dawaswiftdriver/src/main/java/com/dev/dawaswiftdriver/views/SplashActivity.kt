package com.dev.dawaswiftdriver.views



import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.Constants
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.ui.AuthActivity
import com.dev.common.ui.auth.AuthViewModel
import com.dev.common.utils.CommonUtils
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.services.DriverService
import com.dev.dawaswiftdriver.services.TrackerService
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000
    private val PERMISSIONS_REQUEST = 1
    fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (manager != null) {
                for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                    if (serviceClass.name == service.service.className)
                        return true
                }
            }
        } catch (exception: Exception) {
           // AlertDialogBuilder.show(this, exception.message)
        }

        return false
    }

    private val permissionlistener = object : PermissionListener {
        override fun onPermissionDenied(deniedPermissions: List<String>) {

        }

        override fun onPermissionGranted() {
            try {
                if (!isMyServiceRunning(DriverService::class.java))
                    startService(Intent(this@SplashActivity, DriverService::class.java))
            } catch (c: Exception) {
                c.printStackTrace()
            }

        }
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            if (PrefrenceManager(this).getLoginStatus() == 0) {
                //startActivityForResult(Intent(this@SplashActivity, AuthActivity::class.java), Constants().GP_SIGN_IN)


                val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                intent.putExtra(Constants().ROLE_CODE, Constants().ROLE_DRIVER)
                startActivityForResult(intent, Constants().GP_SIGN_IN)

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
        com.dev.common.utils.viewUtils.ViewUtils().makeFullScreen(this)
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
        com.dev.common.utils.viewUtils.ViewUtils().makeFullScreen(this)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
          if (resultCode == Activity.RESULT_OK) {

                startApp()
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
        checkPermissions()

        val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()

    }
    private fun checkPermissions() {
        if (!CommonUtils().isGPSEnabled(this)) {
//            AlertDialogBuilder.show(
//                this,
//                getString(R.string.message_enable_gps),
//                AlertDialogBuilder.DialogButton.CANCEL_RETRY,
//                { result ->
//                    if (result == AlertDialogBuilder.DialogResult.RETRY) {
//                        checkPermissions()
//                    } else {
                       finishAffinity()
//                    }
//                })
            return
        }
        if (CommonUtils().isInternetDisabled(this)) {
//            AlertDialogBuilder.show(
//                this,
//                getString(R.string.message_internet_connection),
//                AlertDialogBuilder.DialogButton.CANCEL_RETRY,
//                { result ->
//                    if (result == AlertDialogBuilder.DialogResult.RETRY) {
//                        checkPermissions()
//                    } else {
//                        finishAffinity()
//                    }
//                })
            return
        }
        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage(getString(R.string.message_permission_denied))
            .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }

    private fun startTrackerService() {
        startService(Intent(this, TrackerService::class.java))
    }

    private fun setUp() {
        // Check GPS is enabled
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (lm != null && !lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show()
            finish()
        }
        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Start the service when the permission is granted
            startTrackerService()
        } else {
            finish()
        }
    }

}
