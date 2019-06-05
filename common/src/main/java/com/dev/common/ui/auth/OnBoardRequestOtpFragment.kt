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
import com.dev.common.listeners.ReplaceFragmentListener
import com.dev.common.models.custom.Status
import com.dev.common.models.oauth.Oauth
import com.dev.common.utils.Validator
import com.dev.common.utils.textWatchers.PhoneTextWatcher
import com.dev.common.R
import com.dev.common.utils.viewUtils.ViewUtils
import kotlinx.android.synthetic.main.onboard_request_otp.*
import kotlinx.android.synthetic.main.toolback_bar.*


class OnBoardRequestOtpFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {

        if (p0 == linear_back) {
            activity?.onBackPressed()
        }
        if (p0 == btn_next) {

            if (Validator.isValidPhoneNumber(edt_phone)) {
                verifyPhone()
            }
        }
    }

    private fun verifyPhone() {

        viewModel.requestOtp(getOauth())


    }

    private fun getOauth(): Oauth {

        val oauth = viewModel.getProfile()
        oauth.profile?.mobile = Validator.getPhoneNumber(edt_phone)
        return oauth

    }


    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    companion object {
        fun newInstance() = OnBoardRequestOtpFragment()
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
        return inflater.inflate(R.layout.onboard_request_otp, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        initWidgets()
        viewModel.observeRequestOtp().observe(this, Observer {

            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {
                replaceFragmentListener.replace(
                    FRAGMENTS_NAV_KEYS.ONBOARD_REQUEST_0TP,
                    FRAGMENTS_NAV_KEYS.ONBOARD_CONFIRM_OTP,
                    getOauth()
                )

            }
        })
    }

    private fun initWidgets() {


        edt_phone.requestFocus()
        btn_next.setOnClickListener(this)
        linear_back.setOnClickListener(this)
        edt_phone.addTextChangedListener(PhoneTextWatcher(edt_phone, txt_ke))
        edt_phone.setText(viewModel.getProfile().profile?.mobile)

    }


}
