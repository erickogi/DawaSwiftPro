package com.dev.common.ui.auth


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.FRAGMENTS_NAV_KEYS
import com.bumptech.glide.Glide
import com.dev.common.listeners.ReplaceFragmentListener
import com.dev.common.models.custom.Status
import com.dev.common.models.oauth.Oauth
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.Validator
import com.dev.common.utils.textWatchers.NameTextWatcher
import com.dev.common.R
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.imagepicker.BottomSheetImagePicker
import com.dev.imagepicker.ButtonType
import kotlinx.android.synthetic.main.onboard_register_names.*
import kotlinx.android.synthetic.main.toolback_bar.*


class OnBoardRegNamesFragment : Fragment(), View.OnClickListener, BottomSheetImagePicker.OnImagesSelectedListener {
    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        Glide.with(this).load(uris[0]).into(avatar)
        val oauth = viewModel.getProfile()
        oauth.profile?.avatarUri = uris[0]
        oauth.profile?.avatar = CommonUtils().encode(uris[0], context!!)
        viewModel.saveProfile(oauth)

    }

    override fun onClick(p0: View?) {

        if (p0 == linear_back) {
            activity?.onBackPressed()
        }
        if (p0 == btn_next) {

            if (Validator.isValidName(edt_first_name) && Validator.isValidEmail(edt_email) && Validator.isValidName(
                    edt_last_name
                )
            ) {
                if (Validator.isRadioGroupdSelected(radio_gender)) {
                    if (getOauth().profile?.avatarUri != null) {
                        verify()
                    } else {
                        edt_avatar.error = "Please select profile image"
                    }
                } else {
                    Toast.makeText(context, "Please select gender ", Toast.LENGTH_LONG).show()

                }
            }
        }
        if (p0 == edt_avatar || p0 == avatar) {
            selectAvatar()
        }
    }

    private fun selectAvatar() {

        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
            .singleSelectTitle(R.string.confirm_pass_title)
            .peekHeight(R.dimen.design_bottom_sheet_peek_height_min)
            .requestTag("single")
            .show(childFragmentManager)
    }

    private fun verify() {


        viewModel.updateProfile(getOauth())
//         replaceFragmentListener.replace(
//                    FRAGMENTS_NAV_KEYS.ONBOARD_NEW_PASSWORD,
//                    FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_SP_2, getOauth())
//

    }

    private fun getOauth(): Oauth {

        val oauth = viewModel.getProfile()
        oauth.profile?.firstName = Validator.getText(edt_first_name)
        oauth.profile?.lastName = Validator.getText(edt_last_name)
        oauth.profile?.email = Validator.getText(edt_email)
        return oauth

    }


    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    companion object {
        fun newInstance() = OnBoardRegNamesFragment()
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
        return inflater.inflate(R.layout.onboard_register_names, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        initWidgets()


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
                replaceFragmentListener.toActivity(FRAGMENTS_NAV_KEYS.ONBOARD_REGISTER_SP_1, getOauth(), true)

            }
        })

        edt_email.setText(viewModel.getProfile().profile?.email)


    }

    private fun initWidgets() {


        edt_first_name.requestFocus()
        btn_next.setOnClickListener(this)
        linear_back.setOnClickListener(this)
        edt_first_name.addTextChangedListener(NameTextWatcher(edt_first_name))
        edt_last_name.addTextChangedListener(NameTextWatcher(edt_last_name))

        edt_avatar.setOnClickListener(this)
        avatar.setOnClickListener(this)

    }


}
