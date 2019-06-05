package com.dev.common.ui.auth


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.FRAGMENTS_NAV_KEYS
import com.dev.common.data.local.PrefrenceManager
import com.dev.common.listeners.ReplaceFragmentListener
import com.dev.common.models.custom.Status
import com.dev.common.models.oauth.Oauth
import com.dev.common.R
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.otpview.OnOtpCompletionListener
import kotlinx.android.synthetic.main.onboard_confirm_otp.*
import kotlinx.android.synthetic.main.toolback_bar.*


class OnBoardConfirmOtpFragment : Fragment(), View.OnClickListener, OnOtpCompletionListener {


    override fun onOtpCompleted(otp: String) {
        verifyOtp(otp)
    }

    override fun onClick(p0: View?) {

        if (p0 == linear_back) {
            activity?.onBackPressed()

        }

        if (p0 == btn_resend) {
            resendOtp()
        }
        if (p0 == btn_validate_otp) {
            verifyOtp(otp)
        }

    }


    private fun resendOtp() {

    }

    private fun verifyOtp(otp: String) {
        this.otp = otp
        viewModel.confirmOtp(getOauth(otp))

    }

//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        val mAuth = FirebaseAuth.getInstance()
//        mAuth.signInWithCredential(credential)
//            .addOnCompleteListener(activity as AuthActivity, OnCompleteListener<AuthResult> { task ->
//                if (task.isSuccessful) {
//
//
//                    val user = task.result!!.user
//                    user.getIdToken(true)
//                        .addOnCompleteListener(activity as AuthActivity, OnCompleteListener<GetTokenResult> {
//
//                            val tk = it.result?.token
//
//                            Log.d(("sdfdzfds"), "TOKEN" + tk)
//                        })
//
//
////                    var ou = user.getOauth("")
////                    ou.profile?.email = user.email
////                    ou.profile?.password = user.uid
////                    viewModel.saveProfile(ou)
////
////                    replaceFragmentListener.replace(
////                        FRAGMENTS_NAV_KEYS.ONBOARD_CONFIRM_OTP,
////                        FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD,
////                        getOauth("")
////                    )
//
//                } else {
//                }
//            })
//    }

    private fun getOauth(otp: String): Oauth {

        val oauth = viewModel.getProfile()
        oauth.profile?.otpCode = otp
        prefrenceManager.saveProfile(oauth)
        viewModel.saveProfile(oauth)

        return oauth
    }


    private lateinit var otp: String
  //  private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var verificationId: String

    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    private lateinit var prefrenceManager: PrefrenceManager

    companion object {
        fun newInstance() = OnBoardConfirmOtpFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = activity as ReplaceFragmentListener
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.onboard_confirm_otp, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        prefrenceManager = PrefrenceManager(context!!)

        initWidgets()
        btn_resend.visibility = View.VISIBLE

//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//            "+254" + getOauth("").profile?.mobile!!,      // Phone number to verify
//            60,               // Timeout duration
//            TimeUnit.SECONDS, // Unit of timeout
//            activity as AuthActivity,             // Activity (for callback binding)
//            callbacks
//        )

        viewModel.observeConfirmOtp().observe(this, Observer {
            ViewUtils.setStatus(activity,view,it.status,it.message,false, ViewUtils.ErrorViewTypes.TOAST,it.exception)
            if(it.status== Status.SUCCESS){
                replaceFragmentListener.replace(
                    FRAGMENTS_NAV_KEYS.ONBOARD_CONFIRM_OTP,
                    FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD, getOauth(otp))

            }
        })
    }

    private fun initWidgets() {


        txt_phone.text = viewModel.getProfile().profile?.mobile
        linear_back.setOnClickListener(this)
        otp_view.setOtpCompletionListener(this)
        btn_resend.setOnClickListener(this)
        btn_validate_otp.setOnClickListener(this)


    }

//    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            // This callback will be invoked in two situations:
//            // 1 - Instant verification. In some cases the phone number can be instantly
//            //     verified without needing to send or enter a verification code.
//            // 2 - Auto-retrieval. On some devices Google Play services can automatically
//            //     detect the incoming verification SMS and perform verification without
//            //     user action.
//
//
//            signInWithPhoneAuthCredential(credential)
//
//            Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
//
//            //signInWithPhoneAuthCredential(credential)
//        }
//
//        override fun onVerificationFailed(e: FirebaseException) {
//            // This callback is invoked in an invalid request for verification is made,
//            // for instance if the the phone number format is not valid.
//            Log.w(ContentValues.TAG, "onVerificationFailed", e)
//
//            if (e is FirebaseAuthInvalidCredentialsException) {
//                // Invalid request
//                // ...
//            } else if (e is FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//                // ...
//            }
//
//            // Show a message and update the UI
//            // ...
//        }
//
//        override fun onCodeSent(
//            verificationd: String?,
//            token: PhoneAuthProvider.ForceResendingToken
//        ) {
//            // The SMS verification code has been sent to the provided phone number, we
//            // now need to ask the user to enter the code and then construct a credential
//            // by combining the code with a verification ID.
//            Log.d(ContentValues.TAG, "onCodeSent:" + verificationd!!)
//            verificationId = verificationd
//            resendToken = token
//            btn_resend.visibility = View.VISIBLE
//
//            // otp_view.setText(verificationId)
//
//
//            // Save verification ID and resending token so we can use them later
////            storedVerificationId = verificationId
////            resendToken = token
//
//            // ...
//        }
//    }


}
