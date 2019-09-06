package com.dev.common.ui.auth


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.R
import com.dev.common.data.FRAGMENTS_NAV_KEYS
import com.dev.common.listeners.BackPressHandler
import com.dev.common.listeners.ReplaceFragmentListener
import com.dev.common.models.custom.Status
import com.dev.common.models.oauth.Oauth
import com.dev.common.models.oauth.Profile
import com.dev.common.ui.AuthActivity
import com.dev.common.utils.Validator
import com.dev.common.utils.textWatchers.PhoneTextWatcher
import com.dev.common.utils.viewUtils.ViewUtils
import kotlinx.android.synthetic.main.onboard_header_view.*
import kotlinx.android.synthetic.main.onboard_phone.*
import kotlinx.android.synthetic.main.toolback_bar.*


class OnBoardPhoneFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        if (p0 == edt_phone) {
            if (headerCardState == ISVISIBLE) {
                changeView()
            }
        }
        if (p0 == linear_back) {
            activity?.onBackPressed()
        }
        if (p0 == btn_next_phone_view || p0 == btn_next_phone_view1) {

            if (Validator.isValidPhoneNumber(edt_phone)) {
                verifyPhone()
            }
        }
    }

    private fun verifyPhone() {


        viewModel.verifyPhone(getOauth())
    }

    private fun getOauth(): Oauth {


        var oauth = viewModel.getProfile()
        if (oauth == null) {
            oauth = Oauth(Profile())
        }

        oauth.profile?.mobile = Validator.getPhoneNumber(edt_phone)
        oauth.profile?.roleId = (activity as AuthActivity).role

        return oauth

    }

    private var headerCardState = 0
    private var ISVISIBLE = 0
    private var ISGONE = 1

    companion object {
        fun newInstance() = OnBoardPhoneFragment()
    }

    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = activity as ReplaceFragmentListener
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.onboard_phone, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        initWidgets()

        viewModel.observeVerifyPhone().observe(this, Observer {


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
                if (it.data?.statusCode == 1) {

                    replaceFragmentListener.replace(
                        FRAGMENTS_NAV_KEYS.ONBOARD_PHONE,
                        FRAGMENTS_NAV_KEYS.ONBOARD_PASSWORD,
                        getOauth()
                    )

                } else {

                    replaceFragmentListener.replace(
                        FRAGMENTS_NAV_KEYS.ONBOARD_PHONE,
                        FRAGMENTS_NAV_KEYS.ONBOARD_REQUEST_0TP,
                        getOauth()
                    )

                }


            }

        })
    }

    private fun initWidgets() {


        edt_phone.requestFocus()
        edt_phone.setOnClickListener(this)
        btn_next_phone_view.setOnClickListener(this)
        btn_next_phone_view1.setOnClickListener(this)
        linear_back.setOnClickListener(this)
        edt_phone.addTextChangedListener(PhoneTextWatcher(edt_phone, txt_ke))


    }

    private fun changeView() {

        if (headerCardState == ISVISIBLE) {
            card_header.visibility = View.GONE
            headerCardState = ISGONE
            linear_back.visibility = View.VISIBLE
            btn_next_phone_view.visibility = View.GONE
            btn_next_phone_view1.visibility = View.VISIBLE
            edt_phone.requestFocus()

        } else {
            context?.let { view?.let { it1 -> ViewUtils.hideSoftKeyBoard(it, it1) } }
            linear_back.visibility = View.GONE
            card_header.visibility = View.VISIBLE
            headerCardState = ISVISIBLE
            btn_next_phone_view.visibility = View.VISIBLE
            btn_next_phone_view1.visibility = View.GONE

        }

    }

    private val backPressHandler = object : BackPressHandler {
        override fun onBackPressed(): Boolean {
            if (headerCardState == ISVISIBLE) {
                activity?.finish()
            } else {
                changeView()
            }
            return false
        }
    }

}
