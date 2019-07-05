package com.dev.dawaswift.ui.chat.ui.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.data.Constants
import com.dev.common.models.ProductSearchAndFilter
import com.dev.common.models.custom.Status
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.Validator
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.chats.ChatsThreadAdapter
import com.dev.dawaswift.models.chat.Message
import com.dev.dawaswift.models.chat.MessageList
import com.dev.dawaswift.models.pharmacy.Pharmacy
import com.dev.dawaswift.ui.CommonMainViewModel
import com.dev.dawaswift.ui.productsearch.ProductSearch
import com.dev.imagepicker.BottomSheetImagePicker
import com.dev.imagepicker.ButtonType
import kotlinx.android.synthetic.main.chat_thread_fragment.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.toolbar_title.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatThreadFragment : Fragment(), BottomSheetImagePicker.OnImagesSelectedListener {
    override fun onImagesSelected(uris: List<Uri>, tag: String?) {

        viewModel.uploadImage(uri = uris[0])

    }


    var pharmacy: Pharmacy? = null
    var message: Message? = null

    companion object {
        fun newInstance() = ChatThreadFragment()
    }

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.chat_thread_fragment, container, false)
    }

    var toMessage: MessageList? = null

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        linear_back.setOnClickListener {
            activity?.onBackPressed()
        }

        try {
            message = arguments?.getSerializable(Constants().MESSAGE) as Message

        } catch (x: Exception) {
            x.printStackTrace()
        }

        try {
            pharmacy = arguments?.getSerializable(Constants().PHARMACY) as Pharmacy

            txt_title.text = pharmacy!!.name
        } catch (x: Exception) {
            x.printStackTrace()
        }



        if (pharmacy == null) {
            activity?.onBackPressed()
        }

        if (message != null) {
            setTypedMessage()
        }

        initChats()
        viewModel.observeChats().observe(this, Observer {

            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {
                updateChats(formatChats(it.data?.data))
            }
        })


        error_btn.setOnClickListener {
            viewModel.fetchOrders()
        }

        viewModel.observeUploadImage().observe(this, Observer {
            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )

            if (it.status == Status.SUCCESS) {
                xmiage.visibility = View.VISIBLE
                CommonUtils().loadImage(context!!, it.data?.data?.urls?.get(0)?.hyperLinkUrl, xmiage)
                if (message == null) {
                    message = Message()
                }
                message?.image = it.data?.data?.urls?.get(0)?.hyperLinkUrl
            }


        })



        avatar.setOnClickListener {

            selectAvatar()
        }



        send.setOnClickListener {

            if (Validator.isValidName(textmessage)) {
                val mes = textmessage.text.toString()
                if (message == null) {
                    message = Message()

                }
                message!!.pharmacyId = pharmacy?.id
                message!!.pharmacyName = pharmacy?.name
                message!!.pharmacyImage = pharmacy?.image
                message!!.message = mes
                message!!.sender = viewModel.getProfile().profile?.id.toString()
                message!!.pharmacyId = pharmacy?.id

                viewModel.createChats(message!!)
            }

        }
        viewModel.observeCreateChat().observe(this, Observer {
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
                xmiage.visibility = View.GONE
                textmessage.setText("")
                fetchMessages()
                message = null

            }

        })



        fetchMessages()
    }

    private fun selectAvatar() {

        BottomSheetImagePicker.Builder(getString(com.dev.common.R.string.file_provider))
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
            .singleSelectTitle(com.dev.common.R.string.pick_single)
            .peekHeight(com.dev.common.R.dimen.peekHeight)
            .requestTag("single")
            .show(childFragmentManager)
    }

    private fun setTypedMessage() {

        textmessage.setText(message?.message)
        if (message?.image != null) {
            CommonUtils().loadImage(context!!, message?.image, xmiage)


            xmiage.visibility = View.VISIBLE

        }
    }


    private fun fetchMessages() {
        viewModel.fetchChats()
    }

    private fun formatChats(data: List<Message>?): List<Message>? {

        var messageList: MutableList<Message> = ArrayList<Message>()
        if (data != null) {
            for (mess in data) {

                if (mess.pharmacyId == pharmacy?.id) {
                    messageList.add(mess)
                }


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

    private fun updateChats(data: List<Message>?) {

        Collections.sort(data, object : Comparator<Message> {
            override fun compare(o1: Message?, o2: Message?): Int {

                return convertToDate(o1!!.createdOn!!).compareTo(convertToDate(o2!!.createdOn!!))

            }

        })
        try {
            items = data as ArrayList<Message>
            adapter.refresh(items)
            recycler_chats.scrollToPosition(items.size - 1)
        } catch (x: Exception) {
            x.printStackTrace()
        }


    }

    fun convertToDate(str: String): Date {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str)
    }

    private lateinit var adapter: ChatsThreadAdapter
    private lateinit var items: ArrayList<Message>

    private fun initChats() {
        items = ArrayList<Message>()
        adapter = context?.let {
            activity?.let { it1 ->
                ChatsThreadAdapter(
                    viewModel.getProfile().profile?.id.toString(),
                    it1,
                    items,
                    object : OnRecyclerViewItemClick {
                        override fun onClickListener(position: Int) {


                            val search: ProductSearchAndFilter = ProductSearchAndFilter()
                            search.productId = items[position].resourceId?.toInt()
                            viewModel.saveSearch(search)
                            startActivity(Intent(context!!, ProductSearch::class.java))


                        }

                        override fun onLongClickListener(position: Int) {

                            showMessage(items[position])

                        }


                    })
            }
        }!!

        var kn = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        kn.stackFromEnd = true

        recycler_chats.layoutManager = kn
        recycler_chats.itemAnimator = DefaultItemAnimator()
        recycler_chats.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    private fun showMessage(message: Message) {
        val mDialogView = LayoutInflater.from(context!!).inflate(com.dev.dawaswift.R.layout.dialog_message, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context!!)
            .setView(mDialogView)
            .setTitle("Message")

        mBuilder.setCancelable(true)




        mBuilder.show()

        var imageview: ImageView = mDialogView.findViewById(R.id.image)
        CommonUtils().loadImage(context!!, message.image, imageview)


    }


}
