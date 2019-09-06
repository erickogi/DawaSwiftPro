package com.dev.dawaswiftdriver.views.account.ui.myprofile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.profile.ProfileActivity
import com.dev.common.ui.web.WebviewActivity
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.viewUtils.OnViewItemClick
import com.dev.common.utils.viewUtils.SimpleDialogModel
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswiftdriver.R
import com.dev.dawaswiftdriver.data.TripsViewModel
import kotlinx.android.synthetic.main.profile_driver.*

class ProfileFragment : Fragment() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000

    private val mRunnable: Runnable = Runnable {


    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: TripsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_driver, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TripsViewModel::class.java)
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

                    CommonUtils().loadImage(context!!, it.avatar, avatar)
                }
            }
        })
        //BadgeView(context).bindTarget(message_notifications).badgeText = "2"
        account.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))


        }




        privacy_policy.setOnClickListener {
            var intent: Intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra("url", "http://resource.calista.co.ke/docs/privacypolicy.html")
            startActivity(intent)


        }
        terms.setOnClickListener {
            var intent: Intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra("url", "http://resource.calista.co.ke/docs/terms.html")
            startActivity(intent)


        }
        returnpolicy.setOnClickListener {

            var intent: Intent = Intent(activity, WebviewActivity::class.java)
            intent.putExtra("url", "http://resource.calista.co.ke/docs/returnpolicy.html")
            startActivity(intent)


        }


    }

}
