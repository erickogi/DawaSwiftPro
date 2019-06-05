package com.dev.cabinzz.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dev.badge.BadgeView
import com.dev.common.listeners.OnViewItemClick
import com.dev.common.models.custom.Status
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.viewUtils.SimpleDialogModel
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.SplashActivity
import com.dev.dawaswift.ui.MainViewModel
import kotlinx.android.synthetic.main.profile.*

class ProfileFragment : Fragment() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000

    private val mRunnable: Runnable = Runnable {


    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        linear_log_out.setOnClickListener {

          ViewUtils.simpleYesNoDialog(context!!,
                "Dawaswift",
                "Please confirm that you want to log out",
                SimpleDialogModel("Yes Proceed", "No", null),
                object : OnViewItemClick {
                    override fun onPositiveClick() {


                        viewModel.logout()
                        activity?.finish()

                    }

                    override fun onNegativeClick() {
                    }

                    override fun onNeutral() {
                    }

                })

        }

        viewModel.liveProfile().observe(this, Observer {

            if (it != null) {
                names.text = it.firstName + "  " + it.lastName
                email.text = it.email

                if (it.avatar != null) {
                    Glide.with(context!!).load(CommonUtils().decode(it.avatar!!)).into(avatar)
                }
            }
        })
        viewModel.observeSwitchProfile().observe(this, Observer {


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
                startActivity(Intent(activity, SplashActivity::class.java))
                activity?.finish()

            }

        })
        BadgeView(context).bindTarget(message_notifications).badgeText = "2"





    }

}
