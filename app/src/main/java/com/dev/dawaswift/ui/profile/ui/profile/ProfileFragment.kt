package com.dev.dawaswift.ui.profile.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.models.custom.Status
import com.dev.common.models.oauth.Oauth
import com.dev.common.ui.auth.AuthViewModel
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.Validator
import com.dev.common.utils.textWatchers.NameTextWatcher
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.imagepicker.BottomSheetImagePicker
import com.dev.imagepicker.ButtonType
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.toolback_bar.*

class ProfileFragment : Fragment(), View.OnClickListener, BottomSheetImagePicker.OnImagesSelectedListener {
    override fun onImagesSelected(uris: List<Uri>, tag: String?) {


        viewModel.uploadImage(uri = uris[0])

    }

    override fun onClick(p0: View?) {

        if (p0 == linear_back) {
            activity?.onBackPressed()
        }
        if (p0 == btn_update) {

            if (Validator.isValidName(edt_first_name) && Validator.isValidEmail(edt_email) && Validator.isValidName(
                    edt_last_name
                ) && Validator.isValidPhone(edt_phone)
            ) {
//                if (Validator.isRadioGroupdSelected(radio_gender)) {
                if (getOauth().profile?.avatar != null) {
                    verify()
                } else {
                    edt_avatar.error = "Please select profile image"
                }
//                } else {
//                    Toast.makeText(context, "Please select gender ", Toast.LENGTH_LONG).show()
//
//                }
            }
        }
        if (p0 == edt_avatar || p0 == avatar) {
            selectAvatar()
        }
    }

    private fun selectAvatar() {

        BottomSheetImagePicker.Builder(getString(com.dev.common.R.string.file_provider))
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
            .singleSelectTitle(com.dev.common.R.string.pick_single)
            .peekHeight(com.dev.common.R.dimen.peekHeight)
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
        oauth.profile?.mobile = Validator.getPhoneNumber(edt_phone)
        return oauth

    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        // TODO: Use the ViewModel
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

                activity?.onBackPressed()
            }
        })

        viewModel.observeUploadImage().observe(this, Observer {
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

                val oauth = viewModel.getProfile()
                oauth.profile?.avatar = it.data?.data?.urls?.get(0)?.hyperLinkUrl


                CommonUtils().loadImage(context!!, oauth.profile?.avatar, avatar)

                viewModel.saveProfile(oauth)

                Toast.makeText(context!!, it.message, Toast.LENGTH_LONG).show()
            }
        })


        edt_email.setText(viewModel.getProfile().profile?.email)


        viewModel.liveProfile().observe(this, Observer {

            if (it != null) {

                edt_first_name.setText(it.firstName)
                edt_last_name.setText(it.lastName)
                edt_email.setText(it.email)
                edt_phone.setText(it.mobile)
                CommonUtils().loadImage(context!!, it.avatar, avatar)


            }
        })
    }

    private fun initWidgets() {


        edt_first_name.requestFocus()
        btn_update.setOnClickListener(this)
        linear_back.setOnClickListener(this)
        edt_first_name.addTextChangedListener(NameTextWatcher(edt_first_name))
        edt_last_name.addTextChangedListener(NameTextWatcher(edt_last_name))

        edt_avatar.setOnClickListener(this)
        avatar.setOnClickListener(this)

    }
}
