package com.dev.dawaswift.ui.cart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.models.custom.Status
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.cart.CartItemsAdapter
import com.dev.dawaswift.models.cart.AddItem
import com.dev.dawaswift.models.cart.CartItem
import com.dev.dawaswift.ui.CommonMainViewModel
import com.dev.lishabora.Utils.OnCartItemEvent
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.cart_fragment.*

class CartFragment : Fragment(), BlockingStep {
    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        callback?.goToPrevStep()
    }

    override fun onSelected() {
        initView()
        init()

    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        callback?.complete()
    }

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {

        callback?.goToNextStep()
    }

    override fun verifyStep(): VerificationError? {

        return null
    }

    override fun onError(error: VerificationError) {

        Toast.makeText(context!!, error.errorMessage, Toast.LENGTH_LONG).show()
    }

    var items: List<CartItem>? = null
    private lateinit var adapter: CartItemsAdapter


    companion object {
        fun newInstance() = CartFragment()
    }

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        observers()


    }


    private fun setUpDetails(data: List<CartItem>?) {
        items = data
        items?.let { adapter.refresh(it) }

    }

    private fun initView() {
        items = ArrayList<CartItem>()
        adapter = context?.let {
            activity?.let { it1 ->
                CartItemsAdapter(0, it1, it, items as ArrayList<CartItem>, object : OnCartItemEvent {
                    override fun delete(pos: Int) {
                        viewModel.deleteFormCart((items as ArrayList<CartItem>)[pos])
                    }

                    override fun add(pos: Int) {
                        if ((items as ArrayList<CartItem>)[pos].quantity!! < 15) {


                            viewModel.addCart(
                                AddItem(
                                    (items as ArrayList<CartItem>)[pos].productId,
                                    (items as ArrayList<CartItem>)[pos].quantity?.plus(1)
                                )
                            )

                        }

                    }

                    override fun remove(pos: Int) {

                        if ((items as ArrayList<CartItem>)[pos].quantity!! > 1) {
                            viewModel.addCart(
                                AddItem(
                                    (items as ArrayList<CartItem>)[pos].productId,
                                    (items as ArrayList<CartItem>)[pos].quantity?.minus(1)
                                )
                            )
                        }

                    }

                })
            }
        }!!


        recyclerCartItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerCartItems.itemAnimator = DefaultItemAnimator()
        recyclerCartItems.adapter = adapter
        adapter.notifyDataSetChanged()


    }


    fun init() {


        viewModel.viewCart()


    }

    fun observers() {


        viewModel.observeCart().observe(this, Observer {

            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            btnCheckOut.visibility = View.GONE

            if (it.status == Status.SUCCESS) {

                //  btnCheckOut.visibility = View.VISIBLE

                if (it != null && it.data != null) {
                    //    btnCheckOut.visibility = View.VISIBLE

                    setUpDetails(it.data!!.data?.cartItems)

                    setUpTotalPrice(it.data!!.data?.totalDiscount)
                } else {
                    btnCheckOut.visibility = View.GONE
                }
            }
        })


    }

    private fun setUpShippingFee(i: Int) {

        //  shippingFee.text = "" + i + ""
    }

    private fun setUpTotalPrice(totalPrice: Int?) {
        total.text = "Total : " + totalPrice + " Ksh"
    }


}
