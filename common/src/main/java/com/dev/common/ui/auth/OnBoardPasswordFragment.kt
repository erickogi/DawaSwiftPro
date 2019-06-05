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
import com.dev.common.utils.textWatchers.PasswordTextWatcher
import com.dev.common.R
import com.dev.common.utils.viewUtils.ViewUtils
import kotlinx.android.synthetic.main.onboard_password.*
import kotlinx.android.synthetic.main.toolback_bar.*


class OnBoardPasswordFragment : Fragment(), View.OnClickListener {
    override fun onClick(p0: View?) {

        if (p0 == linear_back) {
            activity?.onBackPressed()
        }
        if (p0 == btn_next_password_view) {
            if (Validator.isValidPassword(edt_password)) {
            }
            verifyPassword(p0)

        }
        if (p0 == txt_forgot_password) {
            //Toast.makeText(context!!,"This is defiantly on my todo list ",Toast.LENGTH_LONG).show()
            verifyPassword(p0)

        }
    }

    private fun verifyPassword(p0: View?) {
        if (p0 == btn_next_password_view) {
            viewModel.signIn(getOauth())
        } else if (p0 == txt_forgot_password) {
            var oauth = getOauth()


            replaceFragmentListener.replace(
                FRAGMENTS_NAV_KEYS.ONBOARD_PASSWORD,
                FRAGMENTS_NAV_KEYS.ONBOARD_REQUEST_0TP,
                getOauth()
            )

        }

    }

    private fun getOauth(): Oauth {
        val oauth = viewModel.getProfile()
        oauth.profile?.password = Validator.getText(edt_password)

        return oauth

    }


    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    companion object {
        fun newInstance() = OnBoardPasswordFragment()
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
        return inflater.inflate(R.layout.onboard_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        initWidgets()
        viewModel.observeSignIn().observe(this, Observer {
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
                replaceFragmentListener.toActivity(FRAGMENTS_NAV_KEYS.ONBOARD_PASSWORD, it.data, true)

            }
        })

    }

    private fun initWidgets() {


        edt_password.requestFocus()
        txt_forgot_password.setOnClickListener(this)
        btn_next_password_view.setOnClickListener(this)
        linear_back.setOnClickListener(this)
        edt_password.addTextChangedListener(PasswordTextWatcher(edt_password))


    }


}
