package com.dev.dawaswift.ui.order.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.cart.OrderItemsAdapter
import com.dev.dawaswift.models.Order.OrderItem
import com.dev.dawaswift.models.Order.OrderItems
import com.dev.dawaswift.ui.order.OrderActivity
import com.dev.lishabora.Utils.OnCartItemEvent
import kotlinx.android.synthetic.main.cart_toolbar.*
import kotlinx.android.synthetic.main.order_fragment.*

class OrderFragment : Fragment() {
    var cartItemsData: OrderItems? = null
    var items: List<OrderItem>? = null
    private lateinit var adapter: OrderItemsAdapter

    companion object {
        fun newInstance() = OrderFragment()
    }

    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        // TODO: Use the ViewModel
        initView()
        init()





        title.text = "Order Summary"
        back.setOnClickListener({ activity?.onBackPressed() })

    }

    fun init() {


        cartItemsData = (activity as OrderActivity).selectedorder


        date.text = cartItemsData?.createdOn

        code.text = cartItemsData?.docNo.toString()



        setUpTotals(cartItemsData?.totalAmount?.toInt(), cartItemsData?.itemsCount!!.toInt())
        setUpBilling()
        setUpDetails(cartItemsData?.orderItems)

        payment_status.text = cartItemsData?.statusName


    }


    private fun setUpShippingFee(i: Int) {

    }

    private fun setUpTotals(totalPrice: Int?, quantity: Int) {
        itemsValue.text = " " + totalPrice + " Ksh"
        itemsQuantity.text = " " + quantity + " "
        total.text = "" + (totalPrice) + "  Ksh"


    }


    private fun setUpBilling() {
        billingName.text = "Mpesa"
        detail.text = cartItemsData?.buyerName
        context?.let { Glide.with(it).load(R.drawable.mpesa).into(image) }


    }

    private fun setUpDetails(data: List<OrderItem>?) {
        items = data
        items?.let { adapter.refresh(it) }

    }

    private fun initView() {
        items = ArrayList<OrderItem>()
        adapter = context?.let {
            activity?.let { it1 ->
                OrderItemsAdapter(1, it1, it, items as ArrayList<OrderItem>, object : OnCartItemEvent {
                    override fun delete(pos: Int) {

                    }

                    override fun add(pos: Int) {


                    }

                    override fun remove(pos: Int) {


                    }

                })
            }
        }!!


        recyclerCartItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerCartItems.itemAnimator = DefaultItemAnimator()
        recyclerCartItems.adapter = adapter
        adapter.notifyDataSetChanged()


    }

}
