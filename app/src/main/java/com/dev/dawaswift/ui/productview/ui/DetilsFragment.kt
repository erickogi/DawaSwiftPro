package com.dev.dawaswift.ui.productview.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.data.Constants
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Product.Product
import com.kogicodes.sokoni.adapters.DetailItemsAdapter
import kotlinx.android.synthetic.main.detail_fragment.*
import java.util.*

class DetilsFragment : Fragment() {

    var details: List<String>? = null
    private lateinit var detailItemsAdapter: DetailItemsAdapter

    companion object {
        fun newInstance() = DetilsFragment()
    }

    private lateinit var product: Product

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        product=arguments?.getSerializable(Constants().PRODUCT) as Product
        setUpDetails(product.details)
        initView()

    }

    private fun setUpDetails(data: List<String>?) {
        details = data

    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        detailItemsAdapter = context?.let {
            activity?.let { it1 ->
                DetailItemsAdapter(it1, it, details , object : OnRecyclerViewItemClick {
                    override fun onClickListener(position: Int) {



                    }

                    override fun onLongClickListener(position: Int) {


                    }
                })
            }
        }!!


        recyclerDetails.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerDetails.itemAnimator = DefaultItemAnimator()
        recyclerDetails.adapter = detailItemsAdapter
        detailItemsAdapter.notifyDataSetChanged()


    }


}
