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
import com.dev.common.utils.AccountActionsConstanr
import com.dev.common.utils.Validator
import com.dev.common.utils.textWatchers.PasswordTextWatcher
import com.dev.common.R
import com.dev.common.utils.viewUtils.ViewUtils
import kotlinx.android.synthetic.main.onboard_newpassword.*
import kotlinx.android.synthetic.main.toolback_bar.*


class OnBoardNewPasswordFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {

        if (p0 == linear_back) {
            activity?.onBackPressed()
        }
        if (p0 == btn_next_new_password_view) {

            if (Validator.passwordsMatch(edt_password, edt_confirm_password)) {
                if (Validator.isValidPassword(edt_password)) {
                    verifyPassword()
                }
            }
        }
    }

    private fun verifyPassword() {
        val oauth = getOauth()
        if (oauth.profile?.accountAction == AccountActionsConstanr().createAccountAction) {
            viewModel.createProfile(oauth)
        } else {
            viewModel.updatePassword(oauth)
        }

    }

    private fun getOauth(): Oauth {
        val oauth = viewModel.getProfile()
        oauth.profile?.password = Validator.getText(edt_password)
        return oauth

    }

    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    companion object {
        fun newInstance() = OnBoardNewPasswordFragment()
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
        return inflater.inflate(R.layout.onboard_newpassword, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        initWidgets()

        viewModel.observeCreateProfile().observe(this, Observer {
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
                    FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD,
                    FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_SP_1,
                    it.data
                )

            }
        })
        viewModel.observeUpdateProfile().observe(this, Observer {
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
                replaceFragmentListener.toActivity(FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD, it.data, true)

            }
        })
        viewModel.observeUpdatePassword().observe(this, Observer {
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
                replaceFragmentListener.toActivity(FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD, it.data, true)

            }
        })
    }

    private fun initWidgets() {


        edt_password.requestFocus()
        btn_next_new_password_view.setOnClickListener(this)
        linear_back.setOnClickListener(this)
        edt_password.addTextChangedListener(PasswordTextWatcher(edt_password))
        edt_confirm_password.addTextChangedListener(PasswordTextWatcher(edt_confirm_password))


    }


}
