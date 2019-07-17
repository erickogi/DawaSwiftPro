package com.dev.dawaswiftdriver.views.requests

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dev.common.models.driver.requests.TripRequest
import com.dev.dawaswiftdriver.R

class RequestCardFragment : Fragment() {
    internal var request: TripRequest? = null
    private var countDownTimer: CountDownTimer? = null
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            request = arguments!!.getSerializable(ARG_REQUEST) as TripRequest
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_request_card, container, false)


    }

    override fun onViewCreated(vieww: View, savedInstanceState: Bundle?) {
        super.onViewCreated(vieww, savedInstanceState)

        countDownTimer = object : CountDownTimer((5 * 60000).toLong(), 50) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
//                if (mListener != null && request != null)
//                   mListener!!.onDecline(request!!)
            }
        }
        countDownTimer!!.start()
        val distamce = vieww.findViewById<TextView>(R.id.text_distance)
        val from = vieww.findViewById<TextView>(R.id.pick_up)
        val to = vieww.findViewById<TextView>(R.id.drop_off)
        val cost = vieww.findViewById<TextView>(R.id.text_earning)
        val buttonAccept = vieww.findViewById<Button>(R.id.button_accept)
        val buttonDecline = vieww.findViewById<Button>(R.id.button_decline)

        var listOfLocations: MutableList<String> = ArrayList()
        for (pickup in request?.pickUpPoints!!) {

            listOfLocations.add(pickup.location!!)
        }

        from.text = listOfLocations.joinToString()

        to.text = request?.dropOffPoint?.location
        cost.text = request?.earnings?.toString()


        distamce.text = getString(R.string.unit_distance, request!!.distance!! / 1000f)
        buttonAccept.setOnClickListener { view ->
            countDownTimer!!.cancel()
            mListener!!.onAccept(request as TripRequest)
        }
        buttonDecline.setOnClickListener { view ->
            countDownTimer!!.cancel()
            mListener!!.onDecline(request as TripRequest)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onAccept(request: TripRequest)
        fun onDecline(request: TripRequest)
    }

    companion object {
        private val ARG_REQUEST = "request"

        fun newInstance(request: TripRequest): RequestCardFragment {
            val fragment = RequestCardFragment()
            val args = Bundle()
            args.putSerializable(ARG_REQUEST, request)
            fragment.arguments = args
            return fragment
        }
    }
}
