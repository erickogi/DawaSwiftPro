package com.dev.dawaswift.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.models.custom.Status
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.order.OrderItemsAdapter
import com.dev.dawaswift.models.Order.OrderItems
import com.dev.dawaswift.ui.order.OrderActivity
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.toolback_bar.*

class OrdersFragment : Fragment() {

    companion object {
        fun newInstance() = OrdersFragment()
    }

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        linear_back.setOnClickListener {
            activity?.onBackPressed()
        }
        initOrders()
        viewModel.fetchOrders()
        viewModel.observeOrders().observe(this, Observer {

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

                updateOrders(it.data?.data)
            }
        })
        error_btn.setOnClickListener {
            viewModel.fetchOrders()
        }
    }

    private fun updateOrders(data: List<OrderItems>?) {

        items = data as ArrayList<OrderItems>
        adapter.refresh(items)

    }

    private lateinit var adapter: OrderItemsAdapter
    private lateinit var items: ArrayList<OrderItems>

    private fun initOrders() {
        items = ArrayList<OrderItems>()
        adapter = context?.let {
            activity?.let { it1 ->
                OrderItemsAdapter(it1, items, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {

                        var intent: Intent = Intent(activity, OrderActivity::class.java)
                        intent.putExtra("data", items[position])
                        startActivity(intent)
                    }

                    override fun onLongClickListener(position: Int) {
                    }


                })
            }
        }!!


        recyclerOrders.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerOrders.itemAnimator = DefaultItemAnimator()
        recyclerOrders.adapter = adapter
        adapter.notifyDataSetChanged()


    }





}
