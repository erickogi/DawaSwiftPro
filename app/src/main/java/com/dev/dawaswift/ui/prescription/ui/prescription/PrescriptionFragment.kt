package com.dev.dawaswift.ui.prescription.ui.prescription

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.data.Constants
import com.dev.common.models.custom.Status
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.models.pharmacy.Pharmacy
import com.dev.dawaswift.ui.CommonMainViewModel
import com.dev.dawaswift.ui.chat.ChatActivity
import com.dev.dawaswift.ui.pharmacies.ui.pharmacy.PharmacyFragment
import com.dev.imagepicker.BottomSheetImagePicker
import com.dev.imagepicker.ButtonType
import kotlinx.android.synthetic.main.prescription_fragment.*

class PrescriptionFragment : Fragment(), BottomSheetImagePicker.OnImagesSelectedListener {
    override fun onImagesSelected(uris: List<Uri>, tag: String?) {


        viewModel.uploadImage(uri = uris[0])

    }

    var message: com.dev.dawaswift.models.chat.Message? = null

    companion object {
        fun newInstance() = PrescriptionFragment()
    }

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.prescription_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        btn_next.isEnabled = true

        image.setOnClickListener {


            selectAvatar()

        }

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

                CommonUtils().loadImage(context!!, it.data?.data?.urls?.get(0)?.hyperLinkUrl, image)
                btn_next.isEnabled = true

                if (message == null) {
                    message = com.dev.dawaswift.models.chat.Message()
                }
                message!!.image = it.data?.data?.urls?.get(0)?.hyperLinkUrl


            }
        })

        btn_next.setOnClickListener {

            if (message != null) {
                var pharmacy: Pharmacy? = null
                try {
                    pharmacy = arguments?.getSerializable(Constants().PHARMACY) as Pharmacy
                } catch (x: Exception) {
                }




                if (pharmacy == null) {

                    val fragment = PharmacyFragment()


                    var bundle: Bundle = Bundle()
                    bundle.apply {
                        putString(Constants().PHARMACYACTION, Constants().TOCHAT)
                    }
                    bundle.apply {
                        putSerializable(Constants().MESSAGE, message)
                    }

                    fragment.arguments = bundle
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                        .addToBackStack("SUB").commit()


                    // activity?.finish()
                } else {

                    val intent = Intent(activity, ChatActivity::class.java)
                    intent.putExtra(Constants().MESSAGE, message)
                    intent.putExtra(Constants().PHARMACY, pharmacy)
                    startActivity(intent)
                    activity?.finish()
                }
            } else {
                Toast.makeText(context!!, "Select Prescription image ", Toast.LENGTH_LONG).show()
            }

        }


    }

    private fun selectAvatar() {

        BottomSheetImagePicker.Builder(getString(com.dev.common.R.string.file_provider))
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
            .singleSelectTitle(com.dev.common.R.string.pick_single)
            .peekHeight(R.dimen.peekHeight)
            .requestTag("single")
            .show(childFragmentManager)
    }

}
