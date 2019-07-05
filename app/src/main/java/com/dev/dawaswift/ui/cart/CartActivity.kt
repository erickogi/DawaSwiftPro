package com.dev.dawaswift.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.cart.CheckOutStepperAdapter
import com.dev.dawaswift.models.Address.Address
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import com.stepstone.stepper.adapter.StepAdapter
import kotlinx.android.synthetic.main.cart_activity.*

class CartActivity : AppCompatActivity(), StepperLayout.StepperListener {
    private var selectedAddress: Address? = null

    override fun onStepSelected(newStepPosition: Int) {

    }

    override fun onError(verificationError: VerificationError?) {
    }

    override fun onReturn() {
    }

    override fun onCompleted(completeButton: View?) {
        val data = Intent()
        data.putExtra("data", 0)
        setResult(RESULT_OK, data)
        finish()
    }

    var mStepperAdapter: StepAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)
        ViewUtils().makeFullScreen(this)

        mStepperAdapter = CheckOutStepperAdapter(supportFragmentManager, this)
        stepperLayout.adapter = mStepperAdapter!!
        stepperLayout.setListener(this)
    }

    fun setAddress(address: Address?) {
        selectedAddress = address
    }

    fun getAddress(): Address? {
        return selectedAddress
    }

}
