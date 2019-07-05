package com.dev.dawaswift.ui.pharmacies.ui.pharmacy

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.data.Constants
import com.dev.common.listeners.OnViewItemClick
import com.dev.common.models.custom.Status
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.SimpleDialogModel
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.GeoLocationUtills
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.pharmacy.PharmacyItemsAdapter
import com.dev.dawaswift.models.pharmacy.Pharmacy
import com.dev.dawaswift.ui.CommonMainViewModel
import com.dev.dawaswift.ui.chat.ChatActivity
import com.dev.dawaswift.ui.prescription.PrescriptionActivity
import com.dev.dawaswift.ui.productsearch.ProductSearch
import kotlinx.android.synthetic.main.pharmacy_fragment.*
import java.util.*

class PharmacyFragment : Fragment() {

    companion object {
        fun newInstance() = PharmacyFragment()
    }

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pharmacy_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)
        initCategories()
        linear_back.setOnClickListener {
            activity?.onBackPressed()
        }
        viewModel.observePhamacies().observe(this, androidx.lifecycle.Observer {

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

                updatePhamacies(it.data?.data)

            }


        })
        viewModel.liveProfile().observe(this, androidx.lifecycle.Observer {
            if (it.lat != null && it.lon != null) {
                val loc = GeoLocationUtills.getAddress(it.lat!!.toDouble(), it.lon!!.toDouble(), context!!)
                txt_location.text = loc.address


            }

        })

        viewModel.pharmacies(LocationSearchModel())

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                if (categoriesAdapter != null) {
                    if (TextUtils.isEmpty(newText)) {
                        categoriesAdapter?.filter?.filter("")
                    } else {
                        categoriesAdapter?.filter?.filter(newText.toString())
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {

                val loc = LocationSearchModel()
                loc.lat = viewModel.getProfile().profile?.lat
                loc.lon = viewModel.getProfile().profile?.lon
                viewModel.pharmacies(loc)


            } else {


                viewModel.pharmacies(LocationSearchModel())


            }
        }


    }

    private fun updatePhamacies(data: List<Pharmacy>?) {

        categories = data
        categoriesAdapter?.refresh(categories)
    }

    private var categories: List<Pharmacy>? = LinkedList()
    private var categoriesAdapter: PharmacyItemsAdapter? = null
    private fun initCategories() {

        categoriesAdapter = context?.let {
            PharmacyItemsAdapter(it, categories, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {

                    val pharmacy = categories!![position]

                    var action = arguments?.getString(Constants().PHARMACYACTION)

                    Log.d("Actionis", action)
                    if (action == Constants().TOPHARMACY) {

                        ViewUtils.simpleYesNoDialog(context!!,
                            "" + pharmacy.name,
                            "",
                            SimpleDialogModel("View Products", "Upload Prescription", "Chat with pharmacy"),
                            object : OnViewItemClick {
                                override fun onPositiveClick() {
                                    val search = viewModel.fetchSearch()
                                    search.pharmacyId = pharmacy.id
                                    search.pharmacyName = pharmacy.name
                                    viewModel.saveSearch(search)


                                    startActivity(Intent(activity, ProductSearch::class.java))
                                }

                                override fun onNegativeClick() {

                                    val intent = Intent(activity, PrescriptionActivity::class.java)
                                    intent.putExtra(Constants().PHARMACY, pharmacy)
                                    startActivity(intent)


                                }

                                override fun onNeutral() {

                                    val intent = Intent(activity, ChatActivity::class.java)
                                    intent.putExtra(Constants().PHARMACY, pharmacy)
                                    startActivity(intent)
                                }

                            }
                        )

                    } else if (action == Constants().TOCHAT) {

                        val message: com.dev.dawaswift.models.chat.Message =
                            arguments?.getSerializable(Constants().MESSAGE) as com.dev.dawaswift.models.chat.Message

                        message.pharmacyId = pharmacy.id
                        message.pharmacyName = pharmacy.name
                        message.pharmacyImage = pharmacy.image

                        val intent = Intent(activity, ChatActivity::class.java)
                        intent.putExtra(Constants().MESSAGE, message)
                        intent.putExtra(Constants().PHARMACY, pharmacy)
                        startActivity(intent)

                        activity?.finish()


                    } else if (action == Constants().TOPRESCRIPTION) {


//                        val intent=Intent(activity, PrescriptionActivity::class.java)
//                        intent.putExtra(Constants().PHARMACY,pharmacy)
//                        startActivity(intent)


                        val intent = Intent(activity, ChatActivity::class.java)
                        //   intent.putExtra(Constants().MESSAGE, message)
                        intent.putExtra(Constants().PHARMACY, pharmacy)
                        startActivity(intent)
                        activity?.finish()

                    }

                }

                override fun onLongClickListener(position: Int) {


                }
            })
        }!!

        recyclerview_categories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_categories.itemAnimator = DefaultItemAnimator()
        recyclerview_categories.adapter = categoriesAdapter
        categoriesAdapter!!.notifyDataSetChanged()

    }

}
