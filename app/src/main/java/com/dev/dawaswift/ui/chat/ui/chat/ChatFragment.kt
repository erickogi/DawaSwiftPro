package com.dev.dawaswift.ui.chat.ui.chat

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
import com.dev.common.data.Constants
import com.dev.common.models.custom.Status
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.chats.ChatItemsAdapter
import com.dev.dawaswift.models.chat.Message
import com.dev.dawaswift.models.chat.MessageList
import com.dev.dawaswift.models.pharmacy.Pharmacy
import com.dev.dawaswift.ui.CommonMainViewModel
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.toolbar_title.*

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        linear_back.setOnClickListener {
            activity?.onBackPressed()
        }
        txt_title.text = "My  Chats"

        fab_add.setOnClickListener {

            val intent = Intent(activity, com.dev.dawaswift.ui.pharmacies.Pharmacy::class.java)
            intent.putExtra(Constants().PHARMACYACTION, Constants().TOPRESCRIPTION)
            startActivity(intent)

            activity?.finish()

        }


        initChats()
        viewModel.fetchChats()
        viewModel.observeChats().observe(this, Observer {

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

                updateChats(formatChats(it.data?.data))
            }

        })

        error_btn.setOnClickListener {
            viewModel.fetchChats()
        }
    }

    private fun formatChats(data: List<Message>?): List<MessageList>? {

        var messageList: MutableList<MessageList> = ArrayList<MessageList>()

        if (data != null) {
            for (mess in data) {

                if (isNotInList(mess.pharmacyId, messageList)) {


                    var messo = MessageList()
                    messo.messages = getAllMessages(mess.pharmacyId, data)
                    messo.message = messo.messages!![0]


                    messageList.add(messo)
                }


            }
        }

        return messageList
    }

    private fun getAllMessages(pharmacyId: Int?, data: List<Message>): MutableList<Message>? {
        var messageList: MutableList<Message> = ArrayList<Message>()

        for (mess in data) {
            if (mess.pharmacyId == pharmacyId) {
                messageList.add(mess)
            }
        }

        return messageList


    }

    private fun isNotInList(pharmacyId: Int?, messageList: MutableList<MessageList>): Boolean {

        for (mess in messageList) {
            if (mess.message?.pharmacyId == pharmacyId) {
                return false
            }

        }
        return true
    }

    private fun updateChats(data: List<MessageList>?) {

        items = data as ArrayList<MessageList>
        adapter.refresh(items)

    }

    private lateinit var adapter: ChatItemsAdapter
    private lateinit var items: ArrayList<MessageList>

    private fun initChats() {
        items = ArrayList<MessageList>()
        adapter = context?.let {
            activity?.let { it1 ->
                ChatItemsAdapter(it1, items, object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {

                        val pharmacy: Pharmacy = Pharmacy()
                        pharmacy.name = items[position].message?.pharmacyName
                        pharmacy.id = items[position].message?.pharmacyId
                        pharmacy.image = items[position].message?.pharmacyImage


                        var fragment = ChatThreadFragment()
                        fragment
                            .arguments = Bundle().apply {
                            putSerializable(Constants().PHARMACY, pharmacy)
                        }
                        activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                            .addToBackStack("SUB").commit()


                    }

                    override fun onLongClickListener(position: Int) {
                    }


                })
            }
        }!!


        recycler_chats.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_chats.itemAnimator = DefaultItemAnimator()
        recycler_chats.adapter = adapter
        adapter.notifyDataSetChanged()


    }


}
