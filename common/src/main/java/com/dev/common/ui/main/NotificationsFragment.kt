package com.dev.common.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.R
import com.dev.common.adapters.GenericAdapter
import com.dev.common.models.Notification
import com.dev.common.models.custom.Status
import com.dev.common.ui.auth.AuthViewModel
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import kotlinx.android.synthetic.main.notifications_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.notifications_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        viewModel.observeNotifications().observe(this, androidx.lifecycle.Observer {
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
                if (it.data?.data != null) {
                    update(it.data.data)
                }


            }
        })
        init()
        viewModel.notifications()


    }


    private fun update(data: List<Notification>?) {
        items = data
        if (items == null) {
            items = ArrayList()
        }
        init()


    }

    private var items: List<Notification>? = LinkedList()
    private var adapter: GenericAdapter<Notification>? = null
    private fun init() {

        adapter = context?.let {
            GenericAdapter(context!!, items!!, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {


                }

                override fun onLongClickListener(position: Int) {


                }
            })
        }!!

        recyclerItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerItems.itemAnimator = DefaultItemAnimator()
        recyclerItems.adapter = adapter
        adapter!!.notifyDataSetChanged()

    }

}
