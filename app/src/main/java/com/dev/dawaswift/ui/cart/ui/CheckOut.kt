package com.dev.dawaswift.ui.cart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.common.models.custom.Status
import com.dev.common.utils.Validator
import com.dev.common.utils.textWatchers.PhoneTextWatcher
import com.dev.common.utils.viewUtils.OnViewItemClick
import com.dev.common.utils.viewUtils.SimpleDialogModel
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Address.SelectedAddress
import com.dev.dawaswift.models.checkout.CheckOutModel
import com.dev.dawaswift.ui.CommonMainViewModel
import com.dev.dawaswift.ui.cart.CartActivity
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.cart_checkout.*

class CheckOut : Fragment(), BlockingStep {
    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
    }

    override fun onSelected() {
        init()

    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        onPay()
    }

    private fun onPay() {

        if (totalOrderCost > 0 && deliveryCost >= 0 && !Validator.isValidPhone(edt_phone)) {

            Toast.makeText(context!!, "Some Details are missing", Toast.LENGTH_LONG).show()
            return
        }


        viewModel.checkOut(
            CheckOutModel(
                "" + (deliveryCost + totalOrderCost),
                "254" + Validator.getPhoneNumber(edt_phone),
                "1"
            )
        )

    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {

        callback?.goToNextStep()
    }

    override fun verifyStep(): VerificationError? {

        if (totalOrderCost > 0 && deliveryCost >= 0 && Validator.isValidPhone(edt_phone)) {

            return null
        }
        return VerificationError("Some details are missing")
    }

    override fun onError(error: VerificationError) {

        Toast.makeText(context!!, error.errorMessage, Toast.LENGTH_LONG).show()
    }


    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.cart_checkout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        observers()
        edt_phone.addTextChangedListener(PhoneTextWatcher(edt_phone, txt_ke))


    }

    var totalOrderCost: Int = 0
    var deliveryCost: Int = 0

    fun observers() {

        viewModel.observeCart().observe(this, Observer {

            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                true,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {


                if (it != null && it.data != null) {

                    setUpTotals(it.data!!.data?.totalDiscount?.toInt(), it.data!!.data?.itemsCount!!.toInt())

                }
            }
        })
        viewModel.observeStepOne().observe(this, Observer {

            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                true,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {


                if (it != null && it.data != null) {

                    setUpDelivery(it.data!!.data?.cost, it.data!!.data?.period)

                }
            }
        })
        viewModel.observeCheckOut().observe(this, Observer {

            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                true,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {
                if (it != null && it.data != null) {

                    ViewUtils.simpleYesNoDialog(
                        context!!,
                        it.data!!.statusMessage!!,
                        "" + it.data!!.data?.customerMessage,
                        SimpleDialogModel("Okay", null, null),
                        object : OnViewItemClick {
                            override fun onPositiveClick() {
                                activity?.onBackPressed()
                            }

                            override fun onNegativeClick() {

                            }

                            override fun onNeutral() {

                            }

                        })

                }
            }
        })


    }

    private fun setUpDelivery(cost: String?, period: String?) {

        deliverytime.text = period
        deliverydistance.text = ""


        val number: Double = cost!!.toDouble()
        val number3digits: Double = String.format("%.3f", number).toDouble()
        val number2digits: Double = String.format("%.2f", number3digits).toDouble()
        val solution: Double = String.format("%.1f", number2digits).toDouble()
        deliveryCost = solution.toInt()


        deliverycost.text = "" + deliveryCost

        totalcost.text = "" + (deliveryCost + totalOrderCost)

    }

    fun init() {


        viewModel.viewCart()

        var add = (activity as CartActivity).getAddress()
        var sa = SelectedAddress(add?.latitude, add?.longitude)
        try {
            sa.id = add?.id

            sa.name = add?.name
        } catch (x: Exception) {
            x.printStackTrace()
        }

        viewModel.stepOne(sa)


    }

    private fun setUpTotals(totalPrice: Int?, quantity: Int) {
        itemsQuantity.text = " " + quantity + " "


        val number: Double = totalPrice!!.toDouble()
        val number3digits: Double = String.format("%.3f", number).toDouble()
        val number2digits: Double = String.format("%.2f", number3digits).toDouble()
        val solution: Double = String.format("%.1f", number2digits).toDouble()
        totalOrderCost = solution.toInt()


        itemsValue.text = " " + totalOrderCost + " Ksh"

        total.text = "" + (totalOrderCost) + "  Ksh"


        totalcost.text = "" + (deliveryCost + totalOrderCost)


    }


}