package com.dev.dawaswift.ui.cart.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.product.ViewPagerAdapter
import com.dev.dawaswift.ui.cart.CartActivity
import com.google.android.material.tabs.TabLayout
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.analytics.*

class DeliveryFeatures : Fragment(), BlockingStep {
    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
    }

    override fun onSelected() {

        fragment.start()
        fragment1.start()
        // viewModel.fetchAddress()

    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        callback?.complete()
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {


        callback?.goToNextStep()
    }

    override fun verifyStep(): VerificationError? {

        if (fragment.getSelectedAddress() != null) {
            (activity as CartActivity).setAddress(fragment.getSelectedAddress()!!)
            return null
        } else if (fragment1.getSelectedPickup() != null) {
            (activity as CartActivity).setPickUp(fragment1.getSelectedPickup()!!)
            return null

        } else {
            if (fragment.getItems() != null && fragment.getItems()!!.size == 1) {
                //  selectedAddress = items[0]
                fragment.setSelectedAddress(fragment.getItems()!![0])
                (activity as CartActivity).setAddress(fragment.getSelectedAddress()!!)

                return null

            }
            // (activity as CartActivity).setAddress(null)

            return VerificationError("Select or add a delivery or pickup address")
        }
    }

    override fun onError(error: VerificationError) {

        Toast.makeText(context!!, error.errorMessage, Toast.LENGTH_LONG).show()
    }


    companion object {
        fun newInstance() = DeliveryFeatures()
    }

    val fragment = DeliveryFragment()
    val fragment1 = DeliveryPickupFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.analytics, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setupViewPager(viewpager)
        tabs.tabGravity = TabLayout.GRAVITY_FILL
        tabs.setupWithViewPager(viewpager)
        setupTabIcons()


    }

    private fun setupViewPager(viewPager: ViewPager) {


        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(fragment, "Blocked")
        adapter.addFragment(fragment1, "Suspended")


        viewPager.offscreenPageLimit = 1
        viewPager.adapter = adapter
    }

    private fun setupTabIcons() {

        tabs.getTabAt(0)?.text = "Delivery"
        tabs.getTabAt(1)?.text = "Pick Up"


    }

}
